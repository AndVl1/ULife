package ru.bmstu.ulife.network.service

import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import ru.bmstu.ulife.data.models.DataList
import ru.bmstu.ulife.data.models.KrResponse
import ru.bmstu.ulife.network.HttpRoutes
import ru.bmstu.ulife.network.initKtorClient

interface EventDetailService {
    suspend fun getEventByEventId(
        userId: String, eventId: String
    ): HttpResponse = initKtorClient().get("${HttpRoutes.BASE_ULIFE_URL}/$userId/$EVENT/$EVENT/$eventId") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header(HttpHeaders.Accept, ContentType.Application.Json)
    }

    suspend fun getData(
    ): KrResponse {
        return try {
            val respText = initKtorClient().get("https://mysafeinfo.com/api/data?list=deadlyworlddisasters&format=json").bodyAsText(Charsets.UTF_8)
            val response: KrResponse.Success = KrResponse.Success(Json.decodeFromString(respText))
            response
        } catch (e: Exception) {
            KrResponse.Error
        }
    }

    companion object Routes {
        const val EVENT = "event"
    }
}

class EventDetailServiceImpl : EventDetailService