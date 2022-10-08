package ru.bmstu.ktorsocket.content.data

sealed class Result(val res: String) {
    object True: Result("true")
    object False: Result("false")
    object Unspecified: Result("unspecified")
}
