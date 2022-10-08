package ru.bmstu.ktorsocket

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import ru.bmstu.ktorsocket.content.ui.VectorsScreen
import ru.bmstu.ulife.uicommon.theme.MainTheme

class MainActivity: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme {
                VectorsScreen()
            }
        }
    }
}