package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class DataModel (
    @SerialName("Year")
    val year: Int,

    @SerialName("Name")
    val name: String,

    @SerialName("Location")
    val location: String,

    @SerialName("Deaths")
    val deaths: Int,

    @SerialName("ID")
    val ID: Int,
)