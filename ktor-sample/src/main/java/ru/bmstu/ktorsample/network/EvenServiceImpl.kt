package ru.bmstu.ktorsample.network

import android.util.Log
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.url

class EvenServiceImpl(private val client: HttpClient) : EvenService {
    override suspend fun getEven(num: Int): EvenResponse {
        return try {
            val response: EvenResponse.Success = client.get { url("${HttpRoutes.BASE_URL}/$num") }.body()
            response
        } catch (e: Exception) {
            Log.e("EvenServiceImpl", "${e.message}")
            EvenResponse.Error
        }
    }
}
