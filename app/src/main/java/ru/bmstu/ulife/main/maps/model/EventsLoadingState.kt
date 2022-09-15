package ru.bmstu.ulife.main.maps.model

sealed class EventsLoadingState {
    object Loading : EventsLoadingState()
    data class Loaded(val data: List<EventModel>) : EventsLoadingState()
    data class Error(val text: String) : EventsLoadingState()
    object Initial : EventsLoadingState()
}
