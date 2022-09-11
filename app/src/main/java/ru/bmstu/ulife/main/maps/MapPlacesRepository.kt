package ru.bmstu.ulife.main.maps

import kotlinx.coroutines.flow.SharedFlow
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.main.maps.model.LoadingState

interface MapPlacesRepository {
    fun getEvents(): LoadingState
}
