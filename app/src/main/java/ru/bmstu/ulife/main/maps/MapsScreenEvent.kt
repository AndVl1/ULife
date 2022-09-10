package ru.bmstu.ulife.main.maps

sealed class MapsScreenEvent {
    object EnterScreen : MapsScreenEvent()
    data class MarkerClicked(val event: MapsScreenEvent) : MapsScreenEvent()
}
