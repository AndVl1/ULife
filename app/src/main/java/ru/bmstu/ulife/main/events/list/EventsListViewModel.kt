package ru.bmstu.ulife.main.events.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.bmstu.ulife.data.repository.MapPlacesRepository
import ru.bmstu.ulife.main.events.maps.model.EventsLoadingState
import ru.bmstu.ulife.main.events.maps.model.SnackbarType
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.mvi.IntentHandler

class EventsListViewModel(
    private val placesRepository: MapPlacesRepository,
) : ViewModel(), IntentHandler<EventsListMviEvent> {
    private val _state = MutableStateFlow<EventsLoadingState>(EventsLoadingState.Initial)
    val state = _state.asStateFlow()

    private val _eventsFlow = MutableStateFlow<List<EventModel>>(emptyList())
    val events = _eventsFlow.asStateFlow()

    override fun handleEvent(event: EventsListMviEvent) {
        when (val current = _state.value) {
            is EventsLoadingState.ShowInfo -> reduce(event, current)
            is EventsLoadingState.Initial -> reduce(event, current)
            is EventsLoadingState.Loaded -> reduce(event, current)
            is EventsLoadingState.Loading -> reduce(event, current)
        }
    }

    private fun reduce(event: EventsListMviEvent, state: EventsLoadingState.Initial) {
        when (event) {
            EventsListMviEvent.EnterScreen -> {
                getEvents()
            }
            is EventsListMviEvent.EventClicked -> {}
            EventsListMviEvent.UpdateRequested -> {
                getEvents()
            }
        }
    }

    private fun reduce(event: EventsListMviEvent, state: EventsLoadingState.Loading) {
        when (event) {
            EventsListMviEvent.EnterScreen -> {
                getEvents()
            }
            is EventsListMviEvent.EventClicked -> {}
            EventsListMviEvent.UpdateRequested -> {
                getEvents()
            }
        }
    }

    private fun reduce(event: EventsListMviEvent, state: EventsLoadingState.ShowInfo) {
        when (event) {
            EventsListMviEvent.EnterScreen -> {
                getEvents()
            }
            is EventsListMviEvent.EventClicked -> {}
            EventsListMviEvent.UpdateRequested -> {
                getEvents()
            }
        }
    }

    private fun reduce(event: EventsListMviEvent, state: EventsLoadingState.Loaded) {
        when (event) {
            EventsListMviEvent.EnterScreen -> {
                getEvents()
            }
            is EventsListMviEvent.EventClicked -> {}
            EventsListMviEvent.UpdateRequested -> {
                getEvents()
            }
        }
    }

    private fun handleInfo(type: SnackbarType) {
        viewModelScope.launch {
            _state.emit(EventsLoadingState.ShowInfo(type.text))
            delay(3000)
            _state.emit(EventsLoadingState.Initial)
        }
    }

    private fun getEvents() {
        viewModelScope.launch {
            _state.emit(EventsLoadingState.Loading)
            delay(1500)
            when (val response = placesRepository.getEvents()) {
                is EventsLoadingState.Loaded -> {
                    _state.emit(response)
                    _eventsFlow.emit(response.data.sortedBy { it.timeStart })
                }
                is EventsLoadingState.ShowInfo -> {
                    handleInfo(SnackbarType.NETWORK_ERROR)
                }
                else -> _state.emit(response)
            }
        }
    }
}
