package ru.bmstu.ulife.data.repository

import ru.bmstu.ulife.data.states.LoadingState

interface MapPlacesRepository {
    fun getEvents(): LoadingState
}
