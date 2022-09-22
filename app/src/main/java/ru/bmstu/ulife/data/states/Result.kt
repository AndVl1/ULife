package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.UserModel

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    data class RegisterSuccess<out T : Any>(val data: UserModel) : Result<T>()
    object CreateEventSuccess : Result<Nothing>()
    data class Error(val code: Int, val message: String? = null) : Result<Nothing>()
}
