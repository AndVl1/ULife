package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ShopModel(
    @SerialName("address")
    val address: String?,
    @SerialName("title")
    val title: String?,
    @SerialName("metro")
    val metro: String?,
    @SerialName("phone")
    val phone: String?,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("url")
    val url: String?,
):java.io.Serializable {
    companion object {
        val EMPTY = ShopModel(
            address = "",
            latitude = -1.0,
            longitude = -1.0,
            title = "",
            metro = "",
            phone = "",
            url = ""
        )
    }
}
