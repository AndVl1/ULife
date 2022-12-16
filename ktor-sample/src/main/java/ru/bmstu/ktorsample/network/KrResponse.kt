package ru.bmstu.ktorsample.network

import kotlinx.serialization.Serializable

sealed class KrResponse {
    @Serializable
    data class Success(
        val resp: ArrayList<KrRespItem>,
    ): KrResponse()
    object Error : KrResponse()
}


