package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class LogInfoModel (
    @SerialName("userid")
    val userId: Int,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("token")
    val token: Int
)