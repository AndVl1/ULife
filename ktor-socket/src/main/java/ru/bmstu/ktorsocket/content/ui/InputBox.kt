package ru.bmstu.ktorsocket.content.ui

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@Composable
// TODO
fun InputBox(value: String) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(4.dp)) {
        TextField(value = "", onValueChange = {})
    }
}