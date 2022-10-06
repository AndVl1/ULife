package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.EventModel


sealed class EventDetailState {
    data class EventSuccess(val eventModel: EventModel) : EventDetailState()
    data class Error(val textId: Int) : EventDetailState()
    object Default : EventDetailState()
}