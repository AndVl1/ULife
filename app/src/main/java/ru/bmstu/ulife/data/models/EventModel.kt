package ru.bmstu.ulife.main.maps.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName

@Serializable
data class EventModel(
    @SerialName("address")
    val address: String?,
    @SerialName("authorId")
    val authorId: Int?,
    @SerialName("categoryTitle")
    val categoryTitle: String?,
    @SerialName("description")
    val description: String?,
    @SerialName("eventAvatar")
    val eventAvatar: String?,
    @SerialName("eventId")
    val eventId: Int?,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("timeEnd")
    val timeEnd: Long?,
    @SerialName("timeStart")
    val timeStart: Long?,
    @SerialName("title")
    val title: String?
):java.io.Serializable {
    companion object {
        val EMPTY = EventModel(
            address = "",
            authorId = -1,
            categoryTitle = "",
            description = "",
            eventAvatar = "",
            eventId = -1,
            latitude = -1.0,
            longitude = -1.0,
            timeEnd = -1,
            timeStart = -1,
            title = "",
        )
    }
}
