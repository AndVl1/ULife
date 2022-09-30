package ru.bmstu.ulife.view_models

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.repository.EventDetailRepository
import ru.bmstu.ulife.data.states.ErrorHandler
import ru.bmstu.ulife.data.states.EventDetailState

class EventViewModel constructor(private val repository: EventDetailRepository) : ViewModel() {
    private val state: MutableSharedFlow<EventDetailState> = MutableStateFlow(EventDetailState.Default)
    private val _state: SharedFlow<EventDetailState> = state.asSharedFlow()

    fun getEventByEventId(userId: String, eventId: String) {
        viewModelScope.launch {
            repository.getEventBeEventId(userId, eventId)
                .catch { onError(it) }
                .collect { state.emit(EventDetailState.EventSuccess(it)) }
        }
    }

    private suspend fun onError(e: Throwable) {
        when(e) {
            is ErrorHandler.AuthorizationError -> state.emit(EventDetailState.Error(R.string.error_authorization))
            is ErrorHandler.AccessForbiddenError -> state.emit(EventDetailState.Error(R.string.error_access_forbidden))
            is ErrorHandler.ResourceNotFoundError -> state.emit(EventDetailState.Error(R.string.error_resource_not_found))
            else -> {
                state.emit(EventDetailState.Error(R.string.error_text_placeholder))
                println("LOG:: error " + e)
            }
        }
    }

    fun getState() = _state
}