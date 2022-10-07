package ru.bmstu.ulife.main.events.list

import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.main.events.maps.model.EventsLoadingState
import ru.bmstu.ulife.data.models.EventModel

@Composable
fun EventsList(
    onEventDetailsClicked: (EventModel) -> Unit,
) {
    val viewModel = getViewModel<EventsListViewModel>()
    val currentState = viewModel.state.collectAsState()
    val events = viewModel.events.collectAsState()
    SwipeRefresh(
        state = rememberSwipeRefreshState(currentState.value == EventsLoadingState.Loading),
        onRefresh = { viewModel.handleEvent(EventsListMviEvent.UpdateRequested) }
    ) {
        if (events.value.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize().scrollable(rememberScrollState(), Orientation.Vertical))
        }
        LazyColumn {
            item {
                Spacer(modifier = Modifier.height(8.dp))
            }
            if (currentState.value == EventsLoadingState.Loading && events.value.isEmpty()) {
                items(10) {
                    EventItem(model = EventModel.EMPTY, onClick = {}, isPlaceholder = true)
                    Spacer(modifier = Modifier.height(8.dp))
                }
            } else {
                items(events.value) { model ->
                    EventItem(model = model, onClick = { onEventDetailsClicked.invoke(model) })
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(EventsListMviEvent.EnterScreen)
    }
}