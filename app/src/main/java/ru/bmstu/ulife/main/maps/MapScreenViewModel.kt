package ru.bmstu.ulife.main.maps

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.shareIn
import kotlinx.coroutines.launch
import ru.bmstu.ulife.main.maps.model.ErrorType
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.main.maps.model.EventsLoadingState
import ru.bmstu.ulife.mvi.IntentHandler
import ru.bmstu.ulife.utils.UserLocation

class MapScreenViewModel(
    private val placesRepository: MapPlacesRepository,
    private val locationTrackingDataSource: LocationTrackingDataSource,
) : ViewModel(), IntentHandler<MapsScreenEvent> {
    private val _state = MutableStateFlow<EventsLoadingState>(EventsLoadingState.Initial)
    val state = _state.asStateFlow()

    private val _eventsFlow = MutableStateFlow<List<EventModel>>(emptyList())
    val events = _eventsFlow.asStateFlow()

    val location = locationTrackingDataSource.getUserLocationFlow().shareIn(viewModelScope, SharingStarted.WhileSubscribed())

    override fun handleEvent(event: MapsScreenEvent) {
        when (val current = _state.value) {
            is EventsLoadingState.Error -> reduce(event, current)
            is EventsLoadingState.Initial -> reduce(event, current)
            is EventsLoadingState.Loaded -> reduce(event, current)
            is EventsLoadingState.Loading -> reduce(event, current)
        }
    }

    private fun reduce(event: MapsScreenEvent, state: EventsLoadingState.Initial) {
        when (event) {
            MapsScreenEvent.EnterScreen -> {
                getEvents()
            }
            is MapsScreenEvent.MarkerClicked -> {}
            is MapsScreenEvent.CurrentLocationClicked -> {
                if (!event.hasPermission) {
                    handleError(ErrorType.LOCATION_UNAVAILABLE)
                }
            }
        }
    }

    private fun reduce(event: MapsScreenEvent, state: EventsLoadingState.Loading) {
        when (event) {
            MapsScreenEvent.EnterScreen -> {
                getEvents()
            }
            is MapsScreenEvent.MarkerClicked -> {}
            is MapsScreenEvent.CurrentLocationClicked -> {
                if (!event.hasPermission) {
                    handleError(ErrorType.LOCATION_UNAVAILABLE)
                }
            }
        }
    }

    private fun reduce(event: MapsScreenEvent, state: EventsLoadingState.Error) {
        when (event) {
            MapsScreenEvent.EnterScreen -> {
                getEvents()
            }
            is MapsScreenEvent.MarkerClicked -> {}
            is MapsScreenEvent.CurrentLocationClicked -> {
                if (!event.hasPermission) {
                    handleError(ErrorType.LOCATION_UNAVAILABLE)
                }
            }
        }
    }

    private fun reduce(event: MapsScreenEvent, state: EventsLoadingState.Loaded) {
        when (event) {
            MapsScreenEvent.EnterScreen -> {
                getEvents()
            }
            is MapsScreenEvent.MarkerClicked -> {}
            is MapsScreenEvent.CurrentLocationClicked -> {
                if (!event.hasPermission) {
                    handleError(ErrorType.LOCATION_UNAVAILABLE)
                }
            }
        }
    }

    private fun handleError(error: ErrorType) {
        viewModelScope.launch {
            _state.emit(EventsLoadingState.Error(error.text))
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
                    _eventsFlow.emit(response.data)
                }
                else -> _state.emit(response)
            }
        }
    }
}
