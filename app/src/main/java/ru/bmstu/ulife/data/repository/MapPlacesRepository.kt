package ru.bmstu.ulife.data.repository

import ru.bmstu.ulife.main.events.maps.model.EventsLoadingState

interface MapPlacesRepository {
    suspend fun getEvents(): EventsLoadingState
}
