package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class DataList(
    @SerialName("DataList")
    val dataList: List<DataModel>,
)