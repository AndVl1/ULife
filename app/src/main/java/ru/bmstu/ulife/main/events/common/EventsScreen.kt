package ru.bmstu.ulife.main.events.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.google.android.gms.maps.model.LatLng
import ru.bmstu.ulife.R
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.uicommon.theme.UlTheme

@OptIn(ExperimentalPagerApi::class)
@Composable
fun EventsScreen(
    onEventDetailsClicked: (EventModel) -> Unit,
    onCreateNewEvent: (LatLng) -> Unit,
    isInDark: Boolean = false,
    onModeSwitchClicked: (() -> Unit)? = null,
) {
    val screens = listOf(
        TabItem.MapsScreen(onCreateNewEvent, onEventDetailsClicked),
        TabItem.ListScreen(onEventDetailsClicked)
    )
    val pagesState = rememberPagerState()
    Scaffold(
        topBar = {
            TopAppBar(
                backgroundColor = UlTheme.colors.primaryBackground,
                elevation = 0.dp,
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Events",
                        color = UlTheme.colors.primaryText,
                        style = UlTheme.typography.toolbar,
                        textAlign = TextAlign.Center
                    )
                    onModeSwitchClicked?.let {
                        Icon(
                            modifier = Modifier
                                .size(24.dp)
                                .clickable { onModeSwitchClicked.invoke() },
                            painter = if (isInDark) {
                                painterResource(id = R.drawable.ic_dark_mode_48)
                            } else {
                                painterResource(id = R.drawable.ic_light_mode_48)
                            },
                            contentDescription = "",
                            tint = UlTheme.colors.primaryText,
                        )
                    }
                }
            }
        },
        backgroundColor = UlTheme.colors.secondaryBackground
    ) { padding ->
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