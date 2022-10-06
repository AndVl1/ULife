package ru.bmstu.ulife.network.service

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.bmstu.ulife.network.initKtorClient

interface EventDetailService {
    suspend fun getEventByEventId(
        userId: String, eventId: String
    ): HttpResponse = initKtorClient().get("http://37.139.33.65:8080/$userId/$EVENT/$EVENT/$eventId") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header(HttpHeaders.Accept, ContentType.Application.Json)
    }

    companion object Routes {
        const val EVENT = "event"
    }
}

class EventDetailServiceImpl : EventDetailService