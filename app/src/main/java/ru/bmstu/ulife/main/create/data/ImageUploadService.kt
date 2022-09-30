package ru.bmstu.ulife.main.create.data

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.onUpload
import io.ktor.client.request.forms.formData
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import ru.bmstu.ulife.BuildConfig
import ru.bmstu.ulife.main.create.model.ImageLoadingResponse
import ru.bmstu.ulife.main.create.model.ImageLoadingState
import ru.bmstu.ulife.network.HttpRoutes

class ImageUploadService(private val ktor: HttpClient) {
    suspend fun uploadImage(imageBase64: String, progressListener: UploadProgressListener): ImageLoadingState {
        return try {
            val loadingResponse: ImageLoadingResponse = ktor.submitFormWithBinaryData(
                url = HttpRoutes.IMAGES_API_URL,
                formData = formData {
                    append("image", imageBase64, Headers.build {
                        append(HttpHeaders.ContentType, "image/*")
                    })
                }
            ) {
                url {
                    parameters.append("key", BuildConfig.IMGBB_API_KEY)
                }
                onUpload { bytesSentTotal, contentLength ->
                    progressListener.onProgress(bytesSentTotal.toFloat() / contentLength)
                }
            }.body()
            if (loadingResponse.success) {
                ImageLoadingState.Success(loadingResponse)
            } else {
                Log.d("UPLOAD", loadingResponse.status.toString())
                ImageLoadingState.Error(loadingResponse.status, "")
            }
        } catch (e: Exception) {
            ImageLoadingState.Error(-1, e.localizedMessage ?: "Some error occurred")
        }
    }

    fun interface UploadProgressListener {
        fun onProgress(progress: Float)
    }
}
