package ru.bmstu.ulife.main.events.common

import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.pagerTabIndicatorOffset
import kotlinx.coroutines.launch
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.applyIf

@OptIn(ExperimentalPagerApi::class)
@Composable
fun Tabs(items: List<TabItem>, pagerState: PagerState) {
    val scope = rememberCoroutineScope()
    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = UlTheme.colors.primaryBackground,
        contentColor = UlTheme.colors.secondaryBackground,
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        items.forEachIndexed { index, tabItem ->
            Tab(
                selected = index == pagerState.currentPage,
                onClick = {
                    scope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
                text = {
                    Text(
                        text = tabItem.title,
                        color = UlTheme.colors.primaryText
                            .copy(alpha = if (index == pagerState.currentPage) 1f else .6f)
                    )
                }
            )
        }
    }
}