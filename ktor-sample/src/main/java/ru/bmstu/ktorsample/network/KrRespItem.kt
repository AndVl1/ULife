package ru.bmstu.ktorsample.network


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class KrRespItem(
    @SerialName("Country")
    val country: String,
    @SerialName("ID")
    val iD: Int,
    @SerialName("Sport")
    val sport: String,
    @SerialName("Url")
    val url: String,
    @SerialName("YearEst")
    val yearEst: Int
)