package ru.bmstu.ulife.main.events.list

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import ru.bmstu.ulife.ext.TimeUtils
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.uicommon.theme.UlTheme

@Composable
fun EventItem(
    model: EventModel,
    onClick: (Int) -> Unit,
) {
    Card(
        modifier = Modifier
            .padding(UlTheme.shape.padding)
            .height(300.dp)
            .clickable(
                interactionSource = MutableInteractionSource(),
                onClick = { onClick.invoke(model.eventId) },
                indication = rememberRipple(bounded = true),
            ),
        shape = UlTheme.shape.cornerStyle,
        backgroundColor = UlTheme.colors.primaryBackground,
        elevation = 4.dp,
    ) {
        Box {
            Column(modifier = Modifier) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .weight(1f),
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(model.eventAvatar)
                        .crossfade(true)
                        .diskCachePolicy(CachePolicy.ENABLED)
                        .build(),
                    contentDescription = "Event avatar",
                    contentScale = ContentScale.FillWidth,
                    loading = {
                        Box(modifier = Modifier.fillMaxSize()) {
                            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                        }
                    }
                )
                Column(
                    modifier = Modifier
                        .padding(horizontal = 8.dp)
                        .wrapContentSize()
                        .weight(.4f)
                ) {
                    Text(
                        text = model.title,
                        color = UlTheme.colors.primaryText,
                        style = UlTheme.typography.body,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = model.description,
                        color = UlTheme.colors.primaryText,
                        style = UlTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = model.address,
                        color = UlTheme.colors.primaryText.copy(alpha = .6f),
                        style = UlTheme.typography.body,
                    )
                }
            }
            Card(
                modifier = Modifier
                    .align(Alignment.TopStart)
//                    .offset(4.dp, 4.dp)
                    .padding(8.dp)
                ,
                backgroundColor = UlTheme.colors.primaryBackground,
                shape = UlTheme.shape.cornerStyle
            ) {
                Text(
                    text = "${TimeUtils.getDateTimeFromLongTimestamp(model.timeStart)} -" +
                            " ${TimeUtils.getDateTimeFromLongTimestamp(model.timeEnd)}",
                    color = UlTheme.colors.primaryText,
                    style = UlTheme.typography.body,
                    modifier = Modifier.padding(4.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun EventPreviewDark() {
    MainTheme(darkTheme = true) {
        EventItem(model = EventPreview, {})
    }
}

@Preview
@Composable
private fun EventPreviewLight() {
    MainTheme(darkTheme = false) {
        EventItem(model = EventPreview, {})
    }
}

private val EventPreview = EventModel(
    address = "МГТУ им. Н.Э. Баумана",
    authorId = 1,
    categoryTitle = "",
    description = "Тестовое событие на базе МГТУ имени Баумана",
    eventAvatar = "https://um.mos.ru/_next/image?url=https%3A%2F%2Fum.mos.ru%2Fupload%2Fiblock%2F76f%2Frotonda(1).jpg&w=3840&q=75",
    eventId = 1,
    latitude = 55.7695155,
    longitude = 37.6820465,
    timeEnd = 1665604801000,
    timeStart = 1665576001000,
    title = "Что-то тестовое",
)
