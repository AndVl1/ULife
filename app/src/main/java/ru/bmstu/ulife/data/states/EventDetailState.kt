package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.data.models.DataModel
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.models.KrResponse


sealed class EventDetailState {
    data class EventSuccess(val eventModel: EventModel) : EventDetailState()
    data class DataSuccess(val krResponse: KrResponse) : EventDetailState()
    data class Error(val textId: Int) : EventDetailState()
    object Default : EventDetailState()
}