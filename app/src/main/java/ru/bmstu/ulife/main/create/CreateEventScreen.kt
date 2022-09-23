package ru.bmstu.ulife.main.create

import android.Manifest
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.LinearProgressIndicator
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import com.google.android.gms.maps.model.LatLng
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.R
import ru.bmstu.ulife.main.create.data.CreateEventViewModel
import ru.bmstu.ulife.main.create.model.CreateScreenEvent
import ru.bmstu.ulife.main.create.model.EventLoadingState
import ru.bmstu.ulife.main.create.model.SimpleUploadModel
import ru.bmstu.ulife.uicommon.helper.UlAlertDialog
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.multiLet
import ru.bmstu.ulife.utils.openAppSystemSettings
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateEventScreen(
    location: LatLng,
    onEventCreated: () -> Unit
) {
    val viewModel = getViewModel<CreateEventViewModel>()
    val eventPhoto: MutableState<Uri?> = remember { mutableStateOf(null) }
    val filesPermissionState = rememberPermissionState(
        Manifest.permission.READ_EXTERNAL_STORAGE,
    )
    val eventPhotoThumbResult = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = { eventPhoto.value = it }
    )
    val loadingState = viewModel.state.collectAsState()
    val loadingProgress = remember {
        mutableStateOf(0f)
    }
    val locationPermissionDialogShown = remember { mutableStateOf(false) }
    val context = LocalContext.current
    val title = remember { mutableStateOf("") }
    val description = remember { mutableStateOf("") }
    val startDateDialogState = rememberMaterialDialogState()
    val startDate: MutableState<LocalDate?> = remember { mutableStateOf(null) }
    val endDateDialogState = rememberMaterialDialogState()
    val endDate: MutableState<LocalDate?> = remember { mutableStateOf(null) }
    val startTimeDialogState = rememberMaterialDialogState()
    val startTime: MutableState<LocalTime?> = remember { mutableStateOf(null) }
    val endTimeDialogState = rememberMaterialDialogState()
    val endTime: MutableState<LocalTime?> = remember { mutableStateOf(null) }
    val startDateTime : LocalDateTime? = multiLet(startDate.value, startTime.value) { date, time ->
        LocalDateTime.of(date, time).plusSeconds(1)
    }
    val endDateTime : LocalDateTime? = multiLet(endDate.value, endTime.value) { date, time ->
        LocalDateTime.of(date, time).plusSeconds(1)
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = UlTheme.colors.primaryBackground,
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
        ) {
            Column(modifier = Modifier
                .fillMaxSize()
                .weight(1f)
            ) {
                Crossfade(targetState = eventPhoto.value) {
                    if (it == null) {
                        Box(
                            modifier = Modifier
                                .background(UlTheme.colors.secondaryBackground)
                                .fillMaxWidth()
                                .aspectRatio((16f / 9))
                                .clickable {
                                    when (val status = filesPermissionState.status) {
                                        is PermissionStatus.Denied -> {
                                            if (status.shouldShowRationale) {
                                                locationPermissionDialogShown.value = true
                                            } else {
                                                filesPermissionState.launchPermissionRequest()
                                            }
                                        }
                                        PermissionStatus.Granted -> {
                                            eventPhotoThumbResult.launch("image/*")
                                        }
                                    }
                                }
                        ) {
                            Image(
                                modifier = Modifier.align(Alignment.Center),
                                painter = painterResource(id = R.drawable.ic_photo_camera), contentDescription = null,
                            )
                        }
                    } else {
                        AsyncImage(
                            modifier = Modifier
                                .fillMaxWidth()
                                .aspectRatio((16f / 9))
                                .clickable {
                                    eventPhotoThumbResult.launch("image/*")
                                },
                            model = it,
                            contentDescription = null,
                            contentScale = ContentScale.FillWidth,
                        )
                    }
                }
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = title.value,
                    onValueChange = { title.value = it },
                    placeholder = { Text(text = "Title") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = UlTheme.colors.primaryText,
                        placeholderColor = UlTheme.colors.primaryText.copy(alpha = .8f)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                TextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    value = description.value,
                    onValueChange = { description.value = it },
                    placeholder = { Text(text = "Description") },
                    colors = TextFieldDefaults.textFieldColors(
                        textColor = UlTheme.colors.primaryText,
                        placeholderColor = UlTheme.colors.primaryText.copy(alpha = .8f)
                    )
                )
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Select start date",
                        style = UlTheme.typography.body,
                        color = UlTheme.colors.primaryText,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    if (startDate.value != null) {
                        Text(
                            text = ": ${startDate.value}",
                            style = UlTheme.typography.body,
                            color = UlTheme.colors.primaryText,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Button(onClick = { startDateDialogState.show() }) {
                        Text(text = "Select")
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Select start time",
                        style = UlTheme.typography.body,
                        color = UlTheme.colors.primaryText,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    if (startTime.value != null) {
                        Text(
                            text = ": ${startTime.value}",
                            style = UlTheme.typography.body,
                            color = UlTheme.colors.primaryText,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Button(onClick = { startTimeDialogState.show() }) {
                        Text(text = "Select")
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Select end date",
                        style = UlTheme.typography.body,
                        color = UlTheme.colors.primaryText,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    if (endDate.value != null) {
                        Text(
                            text = ": ${endDate.value}",
                            style = UlTheme.typography.body,
                            color = UlTheme.colors.primaryText,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Button(onClick = { endDateDialogState.show() }) {
                        Text(text = "Select")
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Row {
                    Text(
                        text = "Select end time",
                        style = UlTheme.typography.body,
                        color = UlTheme.colors.primaryText,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    if (endTime.value != null) {
                        Text(
                            text = ": ${endTime.value}",
                            style = UlTheme.typography.body,
                            color = UlTheme.colors.primaryText,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                    Spacer(modifier = Modifier.width(2.dp))
                    Button(onClick = { endTimeDialogState.show() }) {
                        Text(text = "Select")
                    }
                }
                startDateTime?.let {
                    Row {
                        Text(
                            text = "Start: $it ${endDateTime?.let { end -> " - End: $end" } ?: ""}",
                            style = UlTheme.typography.body,
                            color = UlTheme.colors.primaryText,
                            modifier = Modifier.align(Alignment.CenterVertically)
                        )
                    }
                }
            }

            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(.1f)
            ) {
                if (loadingState.value is EventLoadingState.Error) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = UlTheme.colors.errorColor
                        )
                        Text(
                            text = (loadingState.value as EventLoadingState.Error).msg,
                            style = UlTheme.typography.caption,
                            color = UlTheme.colors.errorColor
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 8.dp)
                        .clickable { loadingState.value !is EventLoadingState.Loading },
                    onClick = {
                        viewModel.handleEvent(
                            CreateScreenEvent.UploadClicked(
                                SimpleUploadModel(
                                    title = title.value,
                                    description = description.value,
                                    startDateTime = startDateTime,
                                    endDateTime = endDateTime,
                                    latLng = location,
                                    image = eventPhoto.value
                                )
                            )
                        )
                    }
                ) {
                    Crossfade(targetState = loadingState.value) {
                        when (it) {
                            is EventLoadingState.Error,
                            EventLoadingState.Ready,
                            EventLoadingState.Initial -> Text(text = "Upload")
                            is EventLoadingState.Loading -> LinearProgressIndicator(
                                progress = it.progress,
                                color = UlTheme.colors.tintColor,
                            )
                        }
                    }
                }
            }
        }
        MaterialDialog(
            dialogState = startDateDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker {
                startDate.value = it
            }
        }
        MaterialDialog(
            dialogState = startTimeDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            timepicker(is24HourClock = true) {
                startTime.value = it
            }
        }
        MaterialDialog(
            dialogState = endDateDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            datepicker {
                endDate.value = it
            }
        }
        MaterialDialog(
            dialogState = endTimeDialogState,
            buttons = {
                positiveButton("Ok")
                negativeButton("Cancel")
            }
        ) {
            timepicker(is24HourClock = true) {
                endTime.value = it
            }
        }
        AnimatedVisibility(visible = locationPermissionDialogShown.value, exit = fadeOut()) {
            Box(modifier = Modifier.fillMaxWidth()) {
                UlAlertDialog(
                    onDismissRequest = { locationPermissionDialogShown.value = false },
                    title = "Permission not granted",
                    text = "You should give app permission for files access in settings",
                    agreeText = "Go to settings",
                    onAgreeClick = { context.openAppSystemSettings() },
                    denyText = "Cancel",
                    onDenyClick = { locationPermissionDialogShown.value = false }
                )
            }
        }

        LaunchedEffect(key1 = loadingState.value,) {
            if (loadingState.value == EventLoadingState.Ready) {
                onEventCreated.invoke()
            }
        }
    }
}
