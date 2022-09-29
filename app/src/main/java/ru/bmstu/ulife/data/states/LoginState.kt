package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.UserWithTokenModel

sealed class LoginState {
    object RegisterSuccess : LoginState()
    data class LoginSuccess(val userWithTokenModel: UserWithTokenModel) : LoginState()
    object LogoutSuccess : LoginState()
    data class Error(val textId: Int) : LoginState()
    object Default : LoginState()
}