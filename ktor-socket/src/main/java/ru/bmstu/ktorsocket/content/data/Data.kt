package ru.bmstu.ktorsocket.content.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(
    @SerialName("v1")
    val v1: String,
    @SerialName("v2")
    val v2: String,
)

@Serializable
data class Response(
    @SerialName("result")
    val res: String
)


