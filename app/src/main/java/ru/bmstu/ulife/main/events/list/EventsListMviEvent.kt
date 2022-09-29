package ru.bmstu.ulife.main.events.list

sealed class EventsListMviEvent {
    object EnterScreen : EventsListMviEvent()
    data class EventClicked(val event: EventsListMviEvent) : EventsListMviEvent()
    object UpdateRequested : EventsListMviEvent()
}
