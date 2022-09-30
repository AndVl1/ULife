package ru.bmstu.ulife.main.common.ui

import com.google.android.gms.maps.model.LatLng
import ru.bmstu.ulife.data.models.EventModel

sealed class Screens {
    object MapsScreen: Screens()
    data class CreateEventScreen(val latLng: LatLng): Screens()
    data class EventDetailsScreen(val eventModel: EventModel): Screens()
}
