package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class EventModel(
    @SerialName("eventid")
    val eventId: Int,

    @SerialName("authorid")
    val authorId: Int,

    @SerialName("title")
    val title: String,

    @SerialName("categorytitle")
    val categoryTitle: String,

    @SerialName("eventavatar")
    val eventAvatar: String?,

    @SerialName("address")
    val address: String,

    @SerialName("description")
    val description: String?,

    @SerialName("timestart")
    val timeStart: String,

    @SerialName("timeend")
    val timeEnd: String,

    @SerialName("latitude")
    val latitude: Float,

    @SerialName("longitude")
    val longitude: Float
)
