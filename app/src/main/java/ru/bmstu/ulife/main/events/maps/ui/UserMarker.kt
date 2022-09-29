package ru.bmstu.ulife.main.events.maps.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import ru.bmstu.ulife.R
import ru.bmstu.ulife.utils.BitmapUtils

@Composable
fun UserMarker(
    state: MarkerState,
) {
    val context = LocalContext.current
    val icon = BitmapUtils.bitmapDescriptor(
        context, R.drawable.ic_person_pin
    )
    Marker(
        state = state,
        icon = icon,
    )
}

