package ru.bmstu.ulife.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.SendToServerUserModel
import ru.bmstu.ulife.data.repository.LoginRepository
import ru.bmstu.ulife.data.states.ErrorHandler
import ru.bmstu.ulife.data.states.LoginState
import ru.bmstu.ulife.data.states.LoginState.RegisterSuccess

class LoginViewModel constructor(private val repository: LoginRepository) : ViewModel() {
    private val state: MutableSharedFlow<LoginState> = MutableStateFlow(LoginState.Default)
    private val _state: SharedFlow<LoginState> = state.asSharedFlow()

    fun register(userModel: SendToServerUserModel) {
        viewModelScope.launch {
            repository.register(userModel)
                .catch { onError(it) }
                .collect { state.emit(RegisterSuccess) }
        }
    }

    fun login(userId: Int) {
        viewModelScope.launch {
            repository.login(userId)
                .catch { onError(it) }
                .collect { state.emit(LoginState.LoginSuccess(it)) }
        }
    }

    fun logout(userId: Int) {
        viewModelScope.launch {
            kotlin.runCatching { repository.logout(userId) }
                .onSuccess { state.emit(LoginState.LogoutSuccess) }
                .onFailure { onError(it) }
        }
    }

    private suspend fun onError(e: Throwable) {
        when(e) {
            is ErrorHandler.AuthorizationError -> state.emit(LoginState.Error(R.string.error_authorization))
            is ErrorHandler.AccessForbiddenError -> state.emit(LoginState.Error(R.string.error_access_forbidden))
            is ErrorHandler.ResourceNotFoundError -> state.emit(LoginState.Error(R.string.error_resource_not_found))
            else -> {
                state.emit(LoginState.Error(R.string.error_text_placeholder))
                println("LOG:: error " + e)
            }
        }
    }

    fun getState() = _state
}