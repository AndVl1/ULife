package ru.bmstu.ulife.event.create.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CreateEventScreen() {
    val eventName = mutableStateOf(remember { "" })
    val eventDescription = mutableStateOf(remember { "" })
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()) {

            TextField(value = eventName.value, onValueChange = {eventName.value = it})
            Spacer(modifier = Modifier.height(4.dp))
            TextField(value = eventDescription.value, onValueChange = { eventDescription.value = it })
            Spacer(modifier = Modifier.height(4.dp))

        }
    }
}