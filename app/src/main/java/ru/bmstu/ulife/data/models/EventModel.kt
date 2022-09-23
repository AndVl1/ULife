package ru.bmstu.ulife.main.maps.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class EventModel(
    @SerialName("address")
    val address: String,
    @SerialName("authorId")
    val authorId: Int,
    @SerialName("categoryTitle")
    val categoryTitle: String,
    @SerialName("description")
    val description: String,
    @SerialName("eventAvatar")
    val eventAvatar: String,
    @SerialName("eventId")
    val eventId: Int,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timeEnd")
    val timeEnd: Long,
    @SerialName("timeStart")
    val timeStart: Long,
    @SerialName("title")
    val title: String
)
