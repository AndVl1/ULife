package ru.bmstu.ktorsocket.content.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("str")
    val str: String
)

@Serializable
data class Response(
    @SerialName("result")
    val res: String
)


