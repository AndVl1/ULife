package ru.bmstu.ulife.main.maps

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.MarkerInfoWindowContent
import com.google.maps.android.compose.MarkerState
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.uicommon.theme.UlTheme

@Composable
fun GoogleMapView(
    modifier: Modifier = Modifier,
    onMapLoaded: () -> Unit,
    cameraPositionState: CameraPositionState,
    events: List<EventModel>,
    onEventDetailsClicked: (EventModel) -> Unit,
    mapUiSettings: MapUiSettings = MapUiSettings()
) {
    GoogleMap(
        modifier = modifier,
        cameraPositionState = cameraPositionState,
        onMapLoaded = onMapLoaded,
        uiSettings = mapUiSettings
    ) {
        events.map { event ->
            MarkerStateModel(event, MarkerState(LatLng(event.latitude.toDouble(), event.longitude.toDouble())))
        }.forEach { model ->
            MarkerInfoWindowContent(
                state = model.marker,
                onClick = { _ ->
                    onEventDetailsClicked.invoke(model.eventModel)
                    false
                },
            ) {
                Card(
                    backgroundColor = UlTheme.colors.secondaryBackground,
                    contentColor = UlTheme.colors.controlColor,
                ) {
                    Row(modifier = Modifier.padding(4.dp)) {
                        Text(
                            text = model.eventModel.title,
                            style = UlTheme.typography.body
                        )
                        Icon(imageVector = Icons.Default.ArrowForward, contentDescription = "go to")
                    }
                }
            }
        }
    }
}

data class MarkerStateModel(
    val eventModel: EventModel,
    val marker: MarkerState
)
