package ru.bmstu.ulife.main.maps

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.rememberCameraPositionState
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.data.states.LoadingState
import ru.bmstu.ulife.uicommon.theme.UlTheme

@Composable
fun MapsScreen() {
    val viewModel = getViewModel<MapScreenViewModel>()
    val currentState = viewModel.state.collectAsState()
    val events = viewModel.events.collectAsState()
    val moscow = LatLng(55.751244, 37.618423)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(moscow, 10f)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {},
            events = events.value,
            onEventDetailsClicked = {}
        )
        AnimatedVisibility(
            visible = currentState.value == LoadingState.Error,
            enter = fadeIn(),
            exit = fadeOut(),
        ) {
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.BottomCenter),
                backgroundColor = UlTheme.colors.secondaryBackground,
                contentColor = UlTheme.colors.controlColor,
                shape = RoundedCornerShape(4.dp)
            ) {
                Text(text = "Error loading places")
            }
        }
    }
    
    LaunchedEffect(key1 = currentState) {
        viewModel.handleEvent(MapsScreenEvent.EnterScreen)
    }
}
