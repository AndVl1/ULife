package ru.bmstu.ulife.uicommon.helper

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.AlertDialog
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role.Companion.Image
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.uicommon.theme.UlTheme

@Composable
fun UlAlertDialog(
    onDismissRequest: () -> Unit,
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    agreeText: String,
    onAgreeClick: () -> Unit,
    denyText: String,
    onDenyClick: () -> Unit,
) {
    Dialog(onDismissRequest = onDismissRequest) {
        UlAlertDialogLayout(
            title = title,
            text = text,
            agreeText = agreeText,
            onAgreeClick = onAgreeClick,
            denyText = denyText,
            onDenyClick = onDenyClick
        )
    }
}

//Layout
@Composable
private fun UlAlertDialogLayout(
    modifier: Modifier = Modifier,
    title: String,
    text: String,
    agreeText: String,
    onAgreeClick: () -> Unit = {},
    denyText: String,
    onDenyClick: () -> Unit = {},
){
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(10.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp,5.dp,10.dp,10.dp),
        elevation = 8.dp
    ) {
        Column(modifier
            .background(UlTheme.colors.secondaryBackground)
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = title,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 5.dp)
                        .fillMaxWidth(),
                    style = UlTheme.typography.heading,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = text,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(top = 10.dp, start = 25.dp, end = 25.dp)
                        .fillMaxWidth(),
                    style = UlTheme.typography.body
                )
            }
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
                    .background(UlTheme.colors.secondaryBackground),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                TextButton(onClick = onDenyClick) {
                    Text(
                        denyText,
                        fontWeight = FontWeight.Bold,
                        color = UlTheme.colors.controlColor,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
                TextButton(onClick = onAgreeClick) {
                    Text(
                        agreeText,
                        fontWeight = FontWeight.ExtraBold,
                        color = UlTheme.colors.tintColor,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun AlertPreview() {
    MainTheme(darkTheme = false) {
        UlAlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = "Error",
            text = "Location permission not granted",
            agreeText = "Go to settings",
            denyText = "Ignore",
            onDenyClick = {},
            onAgreeClick = {},
        )
    }
}

@Composable
@Preview
private fun DarkAlertPreview() {
    MainTheme(darkTheme = true) {
        UlAlertDialog(
            onDismissRequest = { /*TODO*/ },
            title = "Error",
            text = "Location permission not granted",
            agreeText = "Go to settings",
            denyText = "Ignore",
            onDenyClick = {},
            onAgreeClick = {},
        )
    }
}
