package ru.bmstu.ulife.main.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.data.states.LoadingState
import ru.bmstu.ulife.mvi.IntentHandler

class MapScreenViewModel(
    private val repository: MapPlacesRepository
) : ViewModel(), IntentHandler<MapsScreenEvent> {
    private val _state = MutableStateFlow<LoadingState>(LoadingState.Initial)
    val state = _state.asStateFlow()

    private val _eventsFlow = MutableStateFlow<List<EventModel>>(emptyList())
    val events = _eventsFlow.asStateFlow()

    override fun handleEvent(event: MapsScreenEvent) {
        when (val current = _state.value) {
            LoadingState.Error -> {
                //TODO()
            }
            is LoadingState.Initial -> reduce(event, current)
            is LoadingState.Loaded -> {
                //TODO()
            }
            LoadingState.Loading -> {
                //TODO()
            }
        }
    }

    private fun reduce(event: MapsScreenEvent, state: LoadingState.Initial) {
        when (event) {
            MapsScreenEvent.EnterScreen -> {
                getEvents()
            }
            is MapsScreenEvent.MarkerClicked -> {}
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            _state.emit(LoadingState.Loading)
            delay(1500)
            when (val response = repository.getEvents()) {
                is LoadingState.Loaded -> {
                    _state.emit(response)
                    _eventsFlow.emit(response.data)
                }
                else -> _state.emit(response)
            }
        }
    }
}
