package ru.bmstu.ulife.data.states

sealed class Result<out T : Any> {
    data class Success<out T : Any>(val data: T) : Result<T>()
    object CreateEventSuccess : Result<Nothing>()
    data class Error(val code: Int, val message: String? = null) : Result<Nothing>()
}
