package ru.bmstu.ulife.main.events.list

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.main.events.maps.MapScreenViewModel
import ru.bmstu.ulife.main.events.maps.model.EventsLoadingState
import ru.bmstu.ulife.main.maps.model.EventModel

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
        LazyColumn {

        }
    }
    LaunchedEffect(key1 = Unit) {
        viewModel.handleEvent(EventsListMviEvent.EnterScreen)
    }
}