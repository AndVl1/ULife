package ru.bmstu.ulife.main.maps

import ru.bmstu.ulife.main.maps.model.EventsLoadingState

interface MapPlacesRepository {
    fun getEvents(): EventsLoadingState
}
