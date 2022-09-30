package ru.bmstu.ulife.main.events.maps.ui

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import ru.bmstu.ulife.R
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.BitmapUtils
import ru.bmstu.ulife.utils.UserLocation

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    onMapLoaded: () -> Unit,
    cameraPositionState: CameraPositionState,
    events: List<EventModel>,
    onEventDetailsClicked: (EventModel) -> Unit,
    mapUiSettings: MapUiSettings = MapUiSettings(),
    userLocation: UserLocation? = null,
    onCreateNewEvent: (Marker) -> Unit,
) {
    val createUserLocation: MutableState<LatLng?> = remember { mutableStateOf(null) }
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapLoaded = onMapLoaded,
        uiSettings = mapUiSettings,
        onMapLongClick = { latLng -> createUserLocation.value = latLng },
        onMapClick = { createUserLocation.value = null }
    ) {
        events.map { event ->
            MarkerStateModel(event, MarkerState(LatLng(event.latitude!!.toDouble(), event.longitude!!.toDouble())))
        }.forEach { model ->
            MarkerInfoWindowContent(
                state = model.marker,
                onInfoWindowClick = {
                    onEventDetailsClicked.invoke(model.eventModel)
                }
            ) {
                Card(
                    backgroundColor = UlTheme.colors.secondaryBackground,
                    contentColor = UlTheme.colors.controlColor,
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = model.eventModel.title.toString(),
                            style = UlTheme.typography.body
                        )
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "go to")
                    }
                }
            }
        }
        userLocation?.let {
            if (it.isFromGps) {
                UserMarker(state = MarkerState(userLocation.latLng))
            }
        }
        createUserLocation.value?.let {
            MarkerInfoWindowContent(
                state = MarkerState(it),
                onInfoWindowClick = onCreateNewEvent,
                icon = BitmapUtils.bitmapDescriptor(LocalContext.current, R.drawable.ic_add_circle),
                onInfoWindowClose = { createUserLocation.value = null }
            ) {
                Card(
                    backgroundColor = UlTheme.colors.secondaryBackground,
                    contentColor = UlTheme.colors.controlColor,
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = "Create event here",
                            style = UlTheme.typography.body
                        )
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "go to")
                    }
                }
            }
        }
        userLocation?.let {
            if (it.isFromGps) {
                UserMarker(state = MarkerState(userLocation.latLng))
            }
        }
    }
}

data class MarkerStateModel(
    val eventModel: EventModel,
    val marker: MarkerState
)
