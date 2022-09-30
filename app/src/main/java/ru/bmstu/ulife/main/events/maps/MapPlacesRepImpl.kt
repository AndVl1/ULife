package ru.bmstu.ulife.main.events.maps

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.bmstu.ulife.BuildConfig
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.main.events.common.PlaceResponse
import ru.bmstu.ulife.main.events.maps.model.EventsLoadingState
import ru.bmstu.ulife.main.events.maps.model.SnackbarType
import ru.bmstu.ulife.network.HttpRoutes

class MapPlacesRepImpl(private val ktor: HttpClient): MapPlacesRepository {
    private val job = Job()

    override suspend fun getEvents(): EventsLoadingState {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Handle $exception in CoroutineExceptionHandler")
        }
        return try {
            withContext(job + Dispatchers.IO + coroutineExceptionHandler) {
                val userId = 1
                val data: MutableList<EventModel> =
                    ktor.get{ url("${HttpRoutes.BASE_ULIFE_URL}/${userId}/event/events/") }
                        .body<List<EventModel>>()
                        .toMutableList()
                val finalData = try {
                    data.map { eventModel ->
                        val place = ktor.get(HttpRoutes.LATLNG_TO_POS_API_URL) {
                            url {
                                parameters.append("access_key", BuildConfig.POSITIONSTACK_API_KEY)
                                parameters.append("query", "${eventModel.latitude},${eventModel.longitude}")
                            }
                        }.bodyAsText(Charsets.UTF_8)
                        val a = Json.decodeFromString<PlaceResponse>(place)
                        eventModel.copy(address = "${a.data.firstOrNull()?.name}")
                    }
                } catch (e: Exception) {
                    Log.e("Repo", e.toString())
                    data
                }
                EventsLoadingState.Loaded(finalData)
            }
        } catch (e: Exception) {
            EventsLoadingState.ShowInfo(SnackbarType.NETWORK_ERROR.text)
        }
    }
}
