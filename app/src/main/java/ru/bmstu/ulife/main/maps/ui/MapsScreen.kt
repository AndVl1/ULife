package ru.bmstu.ulife.main.maps.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.R
import ru.bmstu.ulife.main.maps.MapScreenViewModel
import ru.bmstu.ulife.main.maps.MapsScreenEvent
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.main.maps.model.EventsLoadingState
import ru.bmstu.ulife.uicommon.helper.UlAlertDialog
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.UserLocation
import ru.bmstu.ulife.utils.openAppSystemSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen(
    onCreateNewEvent: (LatLng) -> Unit,
    onEventDetailsClicked: (EventModel) -> Unit,
) {
    val viewModel = getViewModel<MapScreenViewModel>()
    val coroutineScope = rememberCoroutineScope()
    val locationPermissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )
    val locationEnabled = remember { mutableStateOf(false) }
    val currentState = viewModel.state.collectAsState()
    val events = viewModel.events.collectAsState()
    val moscow = UserLocation(LatLng(55.751244, 37.618423), false)
    val currentLocation: State<UserLocation> = viewModel.location.collectAsState(initial = moscow)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentLocation.value.latLng, zoom)
    }
    val locationPermissionDialogShown = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val infiniteTransition = rememberInfiniteTransition()
    val angle by infiniteTransition.animateFloat(
        initialValue = 0F,
        targetValue = 360F,
        animationSpec = infiniteRepeatable(
            animation = tween(2000, easing = LinearEasing)
        )
    )

    Box(modifier = Modifier.fillMaxSize()) {
        GoogleMapView(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            onMapLoaded = {},
            events = events.value,
            onEventDetailsClicked = onEventDetailsClicked,
            userLocation = currentLocation.value,
            onCreateNewEvent = { onCreateNewEvent.invoke(it.position) },
        )
        Button(
            modifier = Modifier.align(Alignment.BottomEnd),
            onClick = {
                when (val status = locationPermissionState.status) {
                    is PermissionStatus.Denied -> {
                        if (status.shouldShowRationale) {
                            locationPermissionDialogShown.value = true
                        } else {
                            locationPermissionState.launchPermissionRequest()
                        }
                    }
                    PermissionStatus.Granted -> {
                        locationEnabled.value = true
                        viewModel.handleEvent(MapsScreenEvent.CurrentLocationClicked(true))
                        if (currentLocation.value.isFromGps) {
                            cameraPositionState.animate(
                                currentLocation.value.latLng,
                                coroutineScope
                            )
                        }
                    }
                }
            },
        ) {
            Text(text = "My pos")
        }
        IconButton(
            modifier = Modifier
                .rotate(
                    if (currentState.value == EventsLoadingState.Loading) {
                        angle
                    } else {
                        0f
                    }
                )
                .align(Alignment.TopCenter),
            onClick = {
                viewModel.handleEvent(MapsScreenEvent.UpdateClicked)
            }
        ) {
            Icon(painter = painterResource(id = R.drawable.ic_refresh_48), contentDescription = "Update")
        }
        AnimatedVisibility(
            visible = currentState.value is EventsLoadingState.ShowInfo,
            enter = slideInVertically { fullHeight -> fullHeight + 32 },
            exit = slideOutVertically { fullHeight -> fullHeight + 32 },
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .height(64.dp)
        ) {
            val infoText = (currentState.value as? EventsLoadingState.ShowInfo)?.text
            Card(
                modifier = Modifier
                    .padding(8.dp)
                    .fillMaxWidth(),
                backgroundColor = UlTheme.colors.secondaryBackground,
                contentColor = UlTheme.colors.primaryText,
                shape = RoundedCornerShape(4.dp),
            ) {
                Box(modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 4.dp)) {
                    Text(
                        modifier = Modifier.align(Alignment.Center),
                        text = infoText ?: "",
                        style = UlTheme.typography.errorToast,
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
        AnimatedVisibility(visible = locationPermissionDialogShown.value, exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                UlAlertDialog(
                    onDismissRequest = { locationPermissionDialogShown.value = false },
                    title = "Permission not granted",
                    text = "You should give app permission for location access in settings",
                    agreeText = "Go to settings",
                    onAgreeClick = { context.openAppSystemSettings() },
                    denyText = "Cancel",
                    onDenyClick = { locationPermissionDialogShown.value = false }
                )
            }
        }
    }
    
    LaunchedEffect(key1 = currentState) {
        viewModel.handleEvent(MapsScreenEvent.EnterScreen)
    }

    LaunchedEffect(key1 = currentLocation.value) {
        cameraPositionState.animate(currentLocation.value.latLng, this)
    }
}

private fun CameraPositionState.animate(latLng: LatLng, scope: CoroutineScope) {
    scope.launch {
        this@animate.animate(
            CameraUpdateFactory.newCameraPosition(
                CameraPosition.fromLatLngZoom(latLng, zoom)
            )
        )
    }
}

private const val zoom = 10f
