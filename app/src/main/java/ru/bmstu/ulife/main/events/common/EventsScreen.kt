package ru.bmstu.ulife.main.events.common

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.LatLng
import ru.bmstu.ulife.main.maps.model.EventModel

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EventsScreen(
    onEventDetailsClicked: (EventModel) -> Unit,
    onCreateNewEvent: (LatLng) -> Unit,
) {
    val screens = listOf(
        TabItem.MapsScreen(onCreateNewEvent, onEventDetailsClicked),
        TabItem.ListScreen(onEventDetailsClicked)
    )
    val pagesState = rememberPagerState()
    Scaffold(topBar = {}) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            Tabs(items = screens, pagerState = pagesState)
            HorizontalPager(
                modifier = Modifier.fillMaxSize(),
                state = pagesState,
                count = screens.size,
                userScrollEnabled = false
            ) { page ->
                screens[page].screen()
            }
        }
    }
}