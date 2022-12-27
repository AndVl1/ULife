package ru.bmstu.ktorsample.main.input

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import ru.bmstu.ktorsample.mvi.IntentHandler
import ru.bmstu.ktorsample.network.KrResponse
import ru.bmstu.ktorsample.network.LoadingState

class InputViewModel(
    private val repository: InputRepository
): ViewModel(), IntentHandler<InputEvent> {
    private val _state = MutableStateFlow<LoadingState>(LoadingState.Initial)
    val state = _state.asSharedFlow()

    override fun handleEvent(event: InputEvent) {
        when (val current = _state.value) {
            is LoadingState.Error -> {
                reduce(event, current)
            }
            is LoadingState.Initial -> {
                reduce(event, current)
            }
            is LoadingState.Loaded -> {}
            is LoadingState.Loading -> {}
        }
    }

    private fun reduce(event: InputEvent, state: LoadingState.Loading) {}

    private fun reduce(event: InputEvent, state: LoadingState.Loaded) {
        when (event) {
            is InputEvent.ButtonClicked -> checkIfEven(event.value)
        }
    }
    private fun reduce(event: InputEvent, state: LoadingState.Error) {
        when (event) {
            is InputEvent.ButtonClicked -> checkIfEven(event.value)
        }
    }
    private fun reduce(event: InputEvent, state: LoadingState.Initial) {
        when (event) {
            is InputEvent.ButtonClicked -> checkIfEven(event.value)
        }
    }

    private fun checkIfEven(value: Int) {
        viewModelScope.launch {
            _state.emit(LoadingState.Loading)
            when (val response = repository.checkEven(value)) {
                KrResponse.Error -> {
                    _state.emit(LoadingState.Error)
                }
                is KrResponse.Success -> {
                    _state.emit(LoadingState.Loaded(response))
                    delay(5000)
                    _state.emit(LoadingState.Initial)
                }
            }
        }
    }
}
