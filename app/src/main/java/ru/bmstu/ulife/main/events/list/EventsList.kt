package ru.bmstu.ulife.main.events.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.main.events.maps.MapScreenViewModel
import ru.bmstu.ulife.main.maps.model.EventModel

@Composable
fun EventsList(
    onEventDetailsClicked: (EventModel) -> Unit,
) {
    val viewModel = getViewModel<MapScreenViewModel>()
    LazyColumn {

    }
    LaunchedEffect(key1 = Unit) {
    }
}