package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.UserModel

sealed class LoginState {
    data class RegisterSuccess(val userModel: UserModel) : LoginState()
    data class LoginSuccess(val token: Int) : LoginState()
    object LogoutSuccess : LoginState()
    data class Error(val textId: Int) : LoginState()
    object Default : LoginState()
}