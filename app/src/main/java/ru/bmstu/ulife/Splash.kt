package ru.bmstu.ulife

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import ru.bmstu.ulife.uicommon.theme.MainTheme

class Splash: ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MainTheme(darkTheme = true) {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier.align(Alignment.Center)) {
                        Button(onClick = { navigateToAuthFlow() }) {
                            Text(text = "Auth flow")
                        }
                        Button(onClick = { navigateToComposeScreens() }) {
                            Text(text = "Full compose flow")
                        }
                    }
                }
            }
        }
    }

    private fun navigateToAuthFlow() {
        val intent = Intent(this, ContainerMainActivity::class.java)
        startActivity(intent)
    }

    private fun navigateToComposeScreens() {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
