package ru.bmstu.ulife.main.events.common
import kotlinx.serialization.Serializable

import kotlinx.serialization.SerialName


@Serializable
data class PlaceResponse(
    @SerialName("data")
    val `data`: List<Data>
)

@Serializable
data class Data(
    @SerialName("administrative_area")
    val administrativeArea: String? = null,
    @SerialName("confidence")
    val confidence: Double?,
    @SerialName("continent")
    val continent: String?,
    @SerialName("country")
    val country: String?,
    @SerialName("country_code")
    val countryCode: String?,
    @SerialName("county")
    val county: String?,
    @SerialName("distance")
    val distance: Double?,
    @SerialName("label")
    val label: String?,
    @SerialName("latitude")
    val latitude: Double?,
    @SerialName("locality")
    val locality: String?,
    @SerialName("longitude")
    val longitude: Double?,
    @SerialName("name")
    val name: String?,
    @SerialName("neighbourhood")
    val neighbourhood: String?,
    @SerialName("number")
    val number: String?,
    @SerialName("postal_code")
    val postalCode: String?,
    @SerialName("region")
    val region: String?,
    @SerialName("region_code")
    val regionCode: String?,
    @SerialName("street")
    val street: String?,
    @SerialName("type")
    val type: String?
)
