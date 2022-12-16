package ru.bmstu.ktorsample.main.input

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandHorizontally
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkHorizontally
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ktorsample.network.KrRespItem
import ru.bmstu.ktorsample.network.KrResponse
import ru.bmstu.ktorsample.network.LoadingState
import ru.bmstu.ulife.uicommon.theme.UlTheme

@Composable
@Preview
fun KrScreen() {
    var currentValue by remember { mutableStateOf("") }
    val viewModel = getViewModel<InputViewModel>()
    val state = viewModel.state.collectAsState(LoadingState.Initial)
    Crossfade(targetState = state.value, animationSpec = tween(500)) { response ->
        when (response) {
            LoadingState.Error,
            LoadingState.Initial,
            LoadingState.Loading -> {
                Box(modifier = Modifier.fillMaxSize()) {
                    Column(modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center)) {
                        TextField(
                            value = currentValue,
                            onValueChange = { currentValue = it },
                            modifier = Modifier.fillMaxWidth(),
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                            textStyle = UlTheme.typography.body
                        )
                        Button(
                            onClick = {
                                viewModel.handleEvent(InputEvent.ButtonClicked(0))
                            },
                            enabled = state.value != LoadingState.Loading,
                            modifier = Modifier.align(Alignment.End)
                        ) {
                            if (state.value == LoadingState.Loading) {
                                CircularProgressIndicator(color = UlTheme.colors.controlColor)
                            } else {
                                Text("Check")
                            }
                        }
                        AnimatedVisibility(
                            visible = state.value == LoadingState.Error,
                            enter = fadeIn() + expandVertically() + expandHorizontally(),
                            exit = fadeOut() + shrinkHorizontally() + shrinkVertically(),
                        ) {
                            Text(
                                text = "Error",
                                color = UlTheme.colors.errorColor,
                                style = UlTheme.typography.body
                            )
                        }
                    }
                }
            }
            is LoadingState.Loaded -> {
                Result(response = response.data)
            }
        }
    }
}

@Composable
private fun Result(response: KrResponse.Success) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn() {
            items(response.resp) {
                Data(data = it)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
    }
}

@Composable
private fun Data(data: KrRespItem) {
    Card(elevation = 4.dp, shape = RoundedCornerShape(4.dp)) {
        Column {
            Text(text = data.country)
            Text(text = data.sport)
        }
    }
}
