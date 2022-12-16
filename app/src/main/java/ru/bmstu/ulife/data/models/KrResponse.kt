package ru.bmstu.ulife.data.models

import kotlinx.serialization.Serializable

sealed class KrResponse {
    @Serializable
    data class Success(
        val resp: ArrayList<DataModel>,
    ): KrResponse()
    object Error : KrResponse()
}