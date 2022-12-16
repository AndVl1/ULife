package ru.bmstu.ulife.source

import io.ktor.client.call.*
import ru.bmstu.ulife.data.models.DataList
import ru.bmstu.ulife.data.models.DataModel
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.models.KrResponse
import ru.bmstu.ulife.data.states.Result
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

    suspend fun getData(): Result<KrResponse> {
        val response = eventDetailService.getData()
        return Result.Success(response)
    }
}