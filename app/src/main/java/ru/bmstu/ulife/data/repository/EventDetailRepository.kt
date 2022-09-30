package ru.bmstu.ulife.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.bmstu.ulife.data.states.ErrorHandler
import ru.bmstu.ulife.data.states.Result
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.source.EventDetailRemoteDataSource
import ru.bmstu.ulife.utils.SharedPreferencesStorage

class EventDetailRepository constructor(
    private val storage: SharedPreferencesStorage,
    private val remoteDataSource: EventDetailRemoteDataSource
) {
    suspend fun getEventBeEventId(userId: String, eventId: String): Flow<EventModel> {
        return flow {
            val responseFeed = remoteDataSource.getEventByEventId(userId, eventId)
            if (responseFeed is Result.Success) {
                emit(responseFeed.data)
            } else if (responseFeed is Result.Error) {
                onError(responseFeed.code)
            }
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