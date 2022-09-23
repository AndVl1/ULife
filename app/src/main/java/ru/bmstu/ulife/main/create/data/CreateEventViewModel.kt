package ru.bmstu.ulife.main.create.data

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.ulife.main.create.model.CreateScreenEvent
import ru.bmstu.ulife.main.create.model.EventLoadingState
import ru.bmstu.ulife.main.create.model.SimpleUploadModel
import ru.bmstu.ulife.mvi.IntentHandler

class CreateEventViewModel(val repository: CreateEventRepository) : ViewModel(), IntentHandler<CreateScreenEvent> {
    private val _state = MutableStateFlow<EventLoadingState>(EventLoadingState.Initial)
    val state = _state.asStateFlow()

    override fun handleEvent(event: CreateScreenEvent) {
        when (val current = _state.value) {
            is EventLoadingState.Error -> reduce(event, current)
            is EventLoadingState.Initial -> reduce(event, current)
            is EventLoadingState.Loading -> reduce(event, current)
            is EventLoadingState.Ready -> reduce(event, current)
        }
    }

    private fun reduce(event: CreateScreenEvent, state: EventLoadingState.Initial) {
        when (event) {
            is CreateScreenEvent.UploadClicked -> {
                uploadData(event.simpleUploadModel)
            }
        }
    }

    private fun reduce(event: CreateScreenEvent, state: EventLoadingState.Loading) {
        when (event) {
            is CreateScreenEvent.UploadClicked -> {
                uploadData(event.simpleUploadModel)
            }
        }
    }

    private fun reduce(event: CreateScreenEvent, state: EventLoadingState.Ready) {
        when (event) {
            is CreateScreenEvent.UploadClicked -> {
                uploadData(event.simpleUploadModel)
            }
        }
    }

    private fun reduce(event: CreateScreenEvent, state: EventLoadingState.Error) {
        when (event) {
            is CreateScreenEvent.UploadClicked -> {
                uploadData(event.simpleUploadModel)
            }
        }
    }

    private fun uploadData(data: SimpleUploadModel) {
        viewModelScope.launch {
            if (data.isValid) {
                val uploadResult = repository.uploadEvent(
                    data
                ) {
                    viewModelScope.launch {
                        _state.emit(EventLoadingState.Loading(it))
                    }
                }
                _state.emit(uploadResult)
            } else {
                _state.emit(EventLoadingState.Error("Invalid data"))
            }
        }
    }
}
