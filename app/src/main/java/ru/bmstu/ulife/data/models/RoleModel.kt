package ru.bmstu.ulife.data.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
class RoleModel (
    @SerialName("roleid")
    val roleId: Int,

    @SerialName("role")
    val role: String
)