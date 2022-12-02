package ru.bmstu.ktorsocket.content.data

sealed class Result(val res: String) {
    data class Success(val str: String) : Result(str)
    object Unspecified: Result("unspecified")
}
