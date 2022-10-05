package ru.bmstu.ulife.source

import io.ktor.client.call.*
import io.ktor.client.statement.*
import ru.bmstu.ulife.data.states.Result
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.network.service.EventDetailService


class EventDetailRemoteDataSource constructor(
    private val eventDetailService: EventDetailService
) {

    suspend fun getEventByEventId(userId: String, eventId: String): Result<EventModel> {
        val response = eventDetailService.getEventByEventId(userId, eventId)
        val body = response.body<EventModel>()
        return if (response.status.value == 200) {
            Result.Success(body)
        } else {
            Result.Error(response.status.value)
        }
    }
}