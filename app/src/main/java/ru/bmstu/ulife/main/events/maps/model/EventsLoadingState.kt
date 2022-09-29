package ru.bmstu.ulife.main.events.maps.model

import ru.bmstu.ulife.main.maps.model.EventModel

sealed class EventsLoadingState {
    object Loading : EventsLoadingState()
    data class Loaded(val data: List<EventModel>) : EventsLoadingState()
    data class ShowInfo(val text: String) : EventsLoadingState()
    object Initial : EventsLoadingState()
}
