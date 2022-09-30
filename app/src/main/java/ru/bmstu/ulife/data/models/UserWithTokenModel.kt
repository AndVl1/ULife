package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserWithTokenModel(
    @SerialName("firstName")
    val firstName: String,

    @SerialName("lastName")
    val lastName: String,

    @SerialName("email")
    val email: String,

    @SerialName("age")
    val age: Int,

    @SerialName("gender")
    val gender: String,

    @SerialName("country")
    val country: String,

    @SerialName("city")
    val city: String,

    @SerialName("password")
    val password: String,

    @SerialName("roleId")
    val roleId: Int,

    @SerialName("id")
    val userId: Int,

    @SerialName("token")
    val token: String
)