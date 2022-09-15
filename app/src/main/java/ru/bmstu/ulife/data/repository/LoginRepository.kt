package ru.bmstu.ulife.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bmstu.ulife.data.models.SendToServerUserModel
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.states.ErrorHandler
import ru.bmstu.ulife.data.utils.SharedPreferencesStorage
import ru.bmstu.ulife.source.LoginRemoteDataSource
import ru.bmstu.ulife.data.states.Result

class LoginRepository constructor(
    private val storage: SharedPreferencesStorage,
    private val remoteDataSource: LoginRemoteDataSource
) {
    suspend fun register(userModel: SendToServerUserModel): Flow<UserModel> {
        return flow {
            val responseFeed = remoteDataSource.register(userModel)
            if (responseFeed is Result.Success) {
                emit(responseFeed.data)
            }
        }
    }

    suspend fun login(userId: Int): Flow<Int> {
        return flow {
            val responseFeed = remoteDataSource.login(userId)
            if (responseFeed is Result.Success) {
                emit(responseFeed.data)
            }
        }
    }

    suspend fun logout(userId: Int) {
        when (val response = remoteDataSource.logout(userId)) {
            is Result.Success -> {
                storage.putAuthToken(0)
            }
            is Result.Error -> onError(response.code)
            else -> {}
        }
    }

    private fun onError(errorCode: Int) {
        when {
            errorCode == 401 -> {
                throw ErrorHandler.AuthorizationError
            }
            errorCode == 403 -> {
                throw ErrorHandler.AccessForbiddenError
            }
            errorCode >= 404 -> {
                throw ErrorHandler.ResourceNotFoundError
            }
        }
    }
}