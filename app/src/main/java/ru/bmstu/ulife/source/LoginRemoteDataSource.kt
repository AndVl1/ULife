package ru.bmstu.ulife.source

import io.ktor.client.call.*
import ru.bmstu.ulife.data.models.SendToServerUserModel
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.models.UserWithTokenModel
import ru.bmstu.ulife.data.states.Result
import ru.bmstu.ulife.network.service.LoginServiceImpl

class LoginRemoteDataSource constructor(
    private val loginService: LoginServiceImpl
) {
    suspend fun register(userModel: SendToServerUserModel): Result<UserModel> {
        val response = loginService.register(userModel)
        return if (response.status.value == 200) {
            Result.RegisterSuccess(response.body())
        } else {
            Result.Error(response.status.value)
        }
    }

    suspend fun login(login: String, password: String): Result<UserWithTokenModel> {
        val response = loginService.login(login, password)
        val body = response.body<UserWithTokenModel>()
        return if (response.status.value == 200) {
            Result.Success(body)
        } else {
            Result.Error(response.status.value)
        }
    }

    suspend fun logout(userId: Int): Result<Unit> {
        val response = loginService.logout(userId)
        val body = response.body<Unit>()
        return if (response.status.value == 200) {
            Result.Success(body)
        } else {
            Result.Error(response.status.value)
        }
    }
}