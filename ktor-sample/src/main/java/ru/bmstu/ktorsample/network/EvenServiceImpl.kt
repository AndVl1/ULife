package ru.bmstu.ktorsample.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.url
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json

class EvenServiceImpl(private val client: HttpClient) : EvenService {
    override suspend fun getEven(num: Int): KrResponse {
        return try {
            val respText = client.get { url(HttpRoutes.BASE_URL) }.bodyAsText(Charsets.UTF_8)
            val response: KrResponse.Success = KrResponse.Success(Json.decodeFromString(respText))
            response
        } catch (e: Exception) {
            Log.e("EvenServiceImpl", "${e.message}")
            KrResponse.Error
        }
    }
}
