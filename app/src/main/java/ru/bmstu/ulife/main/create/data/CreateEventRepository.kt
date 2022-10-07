package ru.bmstu.ulife.main.create.data

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.firebase.crashlytics.FirebaseCrashlytics
import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.request.url
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.main.create.model.CreateEventModel
import ru.bmstu.ulife.main.create.model.EventLoadingState
import ru.bmstu.ulife.main.create.model.ImageLoadingState
import ru.bmstu.ulife.main.create.model.SimpleUploadModel
import ru.bmstu.ulife.network.HttpRoutes
import ru.bmstu.ulife.utils.SharedPreferencesStorage
import ru.bmstu.ulife.utils.toBase64
import java.time.format.DateTimeFormatter

class CreateEventRepository(
    private val imageUploadService: ImageUploadService,
    private val ktor: HttpClient,
    private var storage: SharedPreferencesStorage,
    private val context: Context,
) {
    suspend fun uploadEvent(
        data: SimpleUploadModel,
        imageUploadProgressListener: ImageUploadService.UploadProgressListener
    ): EventLoadingState =
        withContext(Dispatchers.IO) {
            data.image?.let {
                val imageUrl = uploadImage(it, imageUploadProgressListener)
                    ?: return@withContext EventLoadingState.Error("Image not uploaded")
                val model = CreateEventModel(
                    authorId = storage.getUserId(),
                    description = data.description,
                    title = data.title,
                    latitude = data.latLng.latitude,
                    longitude = data.latLng.longitude,
                    timeStart = data.startDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) ?: "",
                    timeEnd = data.endDateTime?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")) ?: "",
                    eventAvatar = imageUrl,
                )
                return@withContext if (uploadData(model)) {
                    EventLoadingState.Ready
                } else {
                    EventLoadingState.Error("Some error")
                }
            } ?: EventLoadingState.Error("Some error")
        }


    private suspend fun uploadImage(
        image: Uri,
        imageUploadProgressListener: ImageUploadService.UploadProgressListener
    ): String? =
        withContext(Dispatchers.IO) {
            val stream = context.contentResolver.openInputStream(image)
            val imageBytes = stream?.readBytes()?.toBase64()
            return@withContext try {
                imageBytes?.let {
                    when (val response = imageUploadService.uploadImage(imageBytes, imageUploadProgressListener)) {
                        is ImageLoadingState.Error -> return@withContext null
                        is ImageLoadingState.Success -> return@withContext response.data.data.url
                    }
                }
            } catch (e: Exception) {
                FirebaseCrashlytics.getInstance().recordException(e)
                return@withContext null
            } finally {
                stream?.close()
            }
        }

    private suspend fun uploadData(data: CreateEventModel): Boolean {
        val userId = 1
        val token = 99162322
        val json = Json.encodeToString(data.copy(categoryTitle = "Test12345", address = "abc", ))
        Log.d("CREATE", "$data\n$json")
        val response = ktor.post {
            url("${HttpRoutes.BASE_ULIFE_URL}/${data.authorId}/event/createEvent/${data.authorId}/token/${token}")
            contentType(ContentType.Application.Json)
            setBody(data.copy(categoryTitle = "Test12345", address = "abc", ))
        }
        return response.status == HttpStatusCode.OK
    }
}
