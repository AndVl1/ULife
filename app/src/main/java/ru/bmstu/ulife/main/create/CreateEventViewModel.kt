package ru.bmstu.ulife.main.create

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class CreateEventViewModel : ViewModel() {
    private val _state = MutableStateFlow<EventLoadingState>(EventLoadingState.Initial)
    val state = _state.asStateFlow()
}
