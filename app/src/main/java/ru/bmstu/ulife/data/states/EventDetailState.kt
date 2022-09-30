package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.UserWithTokenModel
import ru.bmstu.ulife.main.maps.model.EventModel


sealed class EventDetailState {
    data class EventSuccess(val eventModel: EventModel) : EventDetailState()
    data class Error(val textId: Int) : EventDetailState()
    object Default : EventDetailState()
}