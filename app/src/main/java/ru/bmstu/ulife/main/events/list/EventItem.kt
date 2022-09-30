package ru.bmstu.ulife.main.events.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.Text
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import ru.bmstu.ulife.ext.TimeUtils
import ru.bmstu.ulife.main.maps.model.EventModel
import ru.bmstu.ulife.uicommon.ext.conditional
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.applyIf

@Composable
fun EventItem(
    model: EventModel,
    isPlaceholder: Boolean = false,
    onClick: () -> Unit,
) {
    val ripple = rememberRipple(bounded = true)
    Card(
        modifier = Modifier
            .padding(UlTheme.shape.padding)
            .height(300.dp)
            .conditional(!isPlaceholder) {
                clickable(
                    interactionSource = MutableInteractionSource(),
                    onClick = onClick,
                    indication = ripple,
                )
            },
        shape = UlTheme.shape.cornerStyle,
        backgroundColor = UlTheme.colors.primaryBackground,
        elevation = 4.dp,
    ) {
        Box {
            Column(modifier = Modifier) {
                SubcomposeAsyncImage(
                    modifier = Modifier
                        .weight(1f)
                        .placeholder(
                            isPlaceholder,
                            color = UlTheme.colors.placeholderColor,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = UlTheme.colors.primaryBackground,
                            )
                        ),
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
                        modifier = Modifier
                            .conditional(isPlaceholder) {
                                size(width = 150.dp, height = 20.dp)
                            }
                            .placeholder(
                                visible = isPlaceholder,
                                color = UlTheme.colors.placeholderColor,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = UlTheme.colors.primaryBackground,
                                )
                            ),
                        text = model.title.toString(),
                        color = if (!isPlaceholder) UlTheme.colors.primaryText else Color.Unspecified,
                        style = if (!isPlaceholder) UlTheme.typography.body else LocalTextStyle.current,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier
                            .conditional(isPlaceholder) {
                                size(width = 250.dp, height = 20.dp)
                            }
                            .placeholder(
                                visible = isPlaceholder,
                                color = UlTheme.colors.placeholderColor,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = UlTheme.colors.primaryBackground,
                                )
                            ),
                        text = model.description.toString(),
                        color = UlTheme.colors.primaryText,
                        style = UlTheme.typography.caption,
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        modifier = Modifier
                            .conditional(isPlaceholder) {
                                size(width = 100.dp, height = 20.dp)
                            }
                            .placeholder(
                                visible = isPlaceholder,
                                color = UlTheme.colors.placeholderColor,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = UlTheme.colors.primaryBackground,
                                )
                            ),
                        text = model.address.toString(),
                        color = UlTheme.colors.primaryText.copy(alpha = .6f),
                        style = UlTheme.typography.body,
                    )
                }
            }
            if (!isPlaceholder) {
                Card(
                    modifier = Modifier
                        .align(Alignment.TopStart)
                        .padding(8.dp),
                    backgroundColor = UlTheme.colors.primaryBackground,
                    shape = UlTheme.shape.cornerStyle
                ) {
                    Text(
                        text = "${TimeUtils.getDateTimeFromLongTimestamp(model.timeStart!!)} -" +
                                " ${TimeUtils.getDateTimeFromLongTimestamp(model.timeEnd!!)}",
                        color = UlTheme.colors.primaryText,
                        style = UlTheme.typography.body,
                        modifier = Modifier.padding(4.dp)
                    )
                }
            }
        }
    }
}

@Preview
@Composable
private fun EventPreviewDark() {
    MainTheme(darkTheme = true) {
        EventItem(model = EventPreview,) {}
    }
}

@Preview
@Composable
private fun EventPreviewLight() {
    MainTheme(darkTheme = false) {
        EventItem(model = EventPreview,) {}
    }
}

@Preview
@Composable
private fun EventPreviewPlaceholderDark() {
    MainTheme(darkTheme = true) {
        EventItem(
            model = EventModel.EMPTY,
            isPlaceholder = true,
        ) {}
    }
}

@Preview
@Composable
private fun EventPreviewPlaceholderLight() {
    MainTheme(darkTheme = false) {
        EventItem(model = EventModel.EMPTY, isPlaceholder = true){}
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
