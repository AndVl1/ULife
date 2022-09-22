package ru.bmstu.ulife.main.create

sealed class EventLoadingState {
    object Initial : EventLoadingState()
    object Loading : EventLoadingState()
    object Error : EventLoadingState()
    object Ready : EventLoadingState()
}
