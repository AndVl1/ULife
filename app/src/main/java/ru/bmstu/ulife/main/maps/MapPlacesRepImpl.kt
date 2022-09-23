package ru.bmstu.ulife.main.maps

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.withContext
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.main.maps.model.EventsLoadingState
import ru.bmstu.ulife.main.maps.model.SnackbarType

class MapPlacesRepImpl(private val ktor: HttpClient): MapPlacesRepository {
    private val job = Job()

    override suspend fun getEvents(): EventsLoadingState {
        val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
            println("Handle $exception in CoroutineExceptionHandler")
        }
        return try {
            withContext(job + Dispatchers.IO + coroutineExceptionHandler) {
                val userId = 1
                val data: List<EventModel> = ktor.get{ url("http://37.139.33.65:8080/${userId}/event/events/") }.body()
                EventsLoadingState.Loaded(data)
            }
        } catch (e: Exception) {
            EventsLoadingState.ShowInfo(SnackbarType.NETWORK_ERROR.text)
        }
    }
}
