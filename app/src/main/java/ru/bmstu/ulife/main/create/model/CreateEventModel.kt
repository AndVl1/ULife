package ru.bmstu.ulife.main.create.model

import android.net.Uri
import com.google.android.gms.maps.model.LatLng
import kotlinx.serialization.Serializable
import kotlinx.serialization.SerialName
import java.time.LocalDateTime

@Serializable
data class CreateEventModel(
    @SerialName("address")
    val address: String = "-",
    @SerialName("authorId")
    val authorId: Int,
    @SerialName("categoryTitle")
    val categoryTitle: String = "Test",
    @SerialName("description")
    val description: String,
    @SerialName("eventAvatar")
    val eventAvatar: String,
    @SerialName("eventId")
    val eventId: Int = 0,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("timeEnd")
    val timeEnd: String,
    @SerialName("timeStart")
    val timeStart: String,
    @SerialName("title")
    val title: String
)

data class SimpleUploadModel(
    val title: String,
    val description: String,
    val startDateTime: LocalDateTime?,
    val endDateTime: LocalDateTime?,
    val image: Uri?,
    val latLng: LatLng,
) {
    val isValid: Boolean
        get() = title.isNotEmpty()
                && description.isNotEmpty()
                && startDateTime != null
                && endDateTime != null
                && image != null

}
