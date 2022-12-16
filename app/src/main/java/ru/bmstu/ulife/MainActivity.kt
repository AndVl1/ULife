package ru.bmstu.ulife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import ru.bmstu.ulife.main.common.ui.MainComposeContent
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.uicommon.theme.baseDarkPalette
import ru.bmstu.ulife.uicommon.theme.baseLightPalette

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val darkTheme = remember {
                mutableStateOf(true)
            }
            MainTheme(darkTheme = darkTheme.value) {
                val systemUiController = rememberSystemUiController()
                val useDarkIcons = !darkTheme.value
                MainComposeContent({}, darkTheme.value, {darkTheme.value = !darkTheme.value})

                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (darkTheme.value) {
                            baseDarkPalette.primaryBackground
                        } else {
                            baseLightPalette.primaryBackground
                        },
                        darkIcons = useDarkIcons
                    )
                }
            }
        }
    }
}
