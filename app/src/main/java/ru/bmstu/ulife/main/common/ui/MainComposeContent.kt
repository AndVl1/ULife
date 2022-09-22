package ru.bmstu.ulife.main.common.ui

import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import ru.bmstu.ulife.main.maps.ui.MapsScreen

@Composable
fun MainComposeContent() {
    val currentScreenState: MutableState<Screens> = remember {
        mutableStateOf(Screens.MapsScreen)
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Crossfade(targetState = currentScreenState.value) { currentScreen ->
            when (currentScreen) {
                is Screens.CreateEventScreen -> TODO()
                is Screens.EventDetailsScreen -> TODO()
                Screens.MapsScreen -> {
                    MapsScreen(
                        onCreateNewEvent = { currentScreenState.value = Screens.CreateEventScreen(it) },
                        onEventDetailsClicked = { currentScreenState.value = Screens.EventDetailsScreen(it) }
                    )
                }
            }
        }
    }
}
