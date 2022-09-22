package ru.bmstu.ulife

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import ru.bmstu.ulife.main.common.ui.MainComposeContent
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.uicommon.theme.UlTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme(darkTheme = true) {
                MainComposeContent()
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Text(text = "Hello $name!", color = UlTheme.colors.primaryText)
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    MainTheme {
        Greeting("Android")
    }
}