package ru.bmstu.ulife.main.events.common

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.google.android.gms.maps.model.LatLng
import ru.bmstu.ulife.main.events.list.EventsList
import ru.bmstu.ulife.data.models.EventModel

sealed class TabItem(
    val title: String,
    val screen: @Composable () -> Unit,
) {
    data class MapsScreen(
        val onCreateNewEvent: (LatLng) -> Unit,
        val onEventDetailsClicked: (EventModel) -> Unit,
    ): TabItem("Map", {
        Log.d("TAB", "map")
        Box(modifier = Modifier.fillMaxSize()) {
            ru.bmstu.ulife.main.events.maps.ui.MapsScreen(
                onCreateNewEvent = onCreateNewEvent,
                onEventDetailsClicked = onEventDetailsClicked
            )
        }
    })
    data class ListScreen(
        val onEventDetailsClicked: (EventModel) -> Unit,
    ): TabItem("List", { EventsList(onEventDetailsClicked) })
}
