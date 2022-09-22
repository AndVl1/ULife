package ru.bmstu.ulife.main.maps.model

import ru.bmstu.ulife.data.models.EventModel

sealed class EventsLoadingState {
    object Loading : EventsLoadingState()
    data class Loaded(val data: List<EventModel>) : EventsLoadingState()
    data class ShowInfo(val text: String) : EventsLoadingState()
    object Initial : EventsLoadingState()
}
