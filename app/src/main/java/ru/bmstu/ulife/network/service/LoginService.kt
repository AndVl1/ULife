package ru.bmstu.ulife.network.service

import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import ru.bmstu.ulife.data.models.SendToServerUserModel
import ru.bmstu.ulife.network.initKtorClient

interface LoginService {
    suspend fun register(
        userModel: SendToServerUserModel
    ): HttpResponse = initKtorClient().post("http://37.139.33.65:8080/$REGISTER") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
        header(HttpHeaders.Accept, ContentType.Application.Json)
        setBody(userModel)
    }

    suspend fun login(
        userId: Int
    ): HttpResponse = initKtorClient().post("http://37.139.33.65:8080/$LOGIN/$userId") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    suspend fun logout(
        userId: Int
    ): HttpResponse = initKtorClient().post("http://37.139.33.65:8080/$LOGOUT/$userId") {
        header(HttpHeaders.ContentType, ContentType.Application.Json)
    }

    companion object Routes {
        const val REGISTER = "register"
        const val LOGIN = "login" //"login/{userId}"
        const val LOGOUT = "logout" //"logout/{userId}"
    }
}

class LoginServiceImpl : LoginService