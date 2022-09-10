package ru.bmstu.ktorsample.network

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

sealed class EvenResponse {
    @Serializable
    data class Success(
        @SerialName("iseven")
        val isEven: Boolean,
        @SerialName("ad")
        val ad: String,
    ): EvenResponse()
    object Error : EvenResponse()
}
