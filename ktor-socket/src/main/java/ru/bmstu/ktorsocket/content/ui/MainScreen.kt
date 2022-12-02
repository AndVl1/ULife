package ru.bmstu.ktorsocket.content.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Divider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ktorsocket.content.data.PeakViewModel
import ru.bmstu.ulife.uicommon.theme.UlTheme

/*
@Composable
fun VectorsScreen() {
    val viewModel = getViewModel<PeakViewModel>()
    val res = viewModel.result.collectAsState()
    val v1 = viewModel.v1.collectAsState()
    val v2 = viewModel.v2.collectAsState()
    val status = viewModel.status.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(text = "Input")
                    Spacer(modifier = Modifier.width(1.dp))
                }
            }
        }
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(text = "Type 2 vectors [size = 3]", style = UlTheme.typography.heading)
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    value = v1.value,
                    onValueChange = {
                        viewModel.onV1Input(it)
                    },
                    placeholder = { Text(text = "vect1") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                )
                TextField(
                    value = v2.value,
                    onValueChange = {
                        viewModel.onV2Input(it)
                    },
                    placeholder = { Text(text = "vect2") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                Divider(modifier = Modifier.fillMaxWidth(), color = UlTheme.colors.primaryText)
                Text(text = res.value.res)
            }
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = if (status.value) "Connected" else "Not connected",
                style = UlTheme.typography.caption,
                color = if (status.value) Color.Green else Color.Red,
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        viewModel.initSockets()
    }
}
*/
