package ru.bmstu.ulife.data.states

import ru.bmstu.ulife.main.maps.model.EventModel

sealed class LoadingState {
    object Loading : LoadingState()
    data class Loaded(val data: List<EventModel>) : LoadingState()
    object Error : LoadingState()
    object Initial : LoadingState()
}
