package ru.bmstu.ulife.main.create

import android.Manifest
import android.graphics.Bitmap
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.Crossfade
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import org.koin.androidx.compose.getViewModel
import ru.bmstu.ulife.R
import ru.bmstu.ulife.uicommon.helper.UlAlertDialog
import ru.bmstu.ulife.uicommon.theme.UlTheme
import ru.bmstu.ulife.utils.openAppSystemSettings

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun CreateEventScreen(location: LatLng) {
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
    val locationPermissionDialogShown = remember { mutableStateOf(false) }
    val context = LocalContext.current
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(color = UlTheme.colors.primaryBackground),
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
                TextField(value = "", onValueChange = {}, modifier = Modifier.fillMaxWidth().padding(4.dp))
            }
            Column(modifier = Modifier
                .fillMaxWidth()
                .weight(.1f)) {
                if (loadingState.value == EventLoadingState.Error) {
                    Row {
                        Icon(
                            imageVector = Icons.Default.Warning,
                            contentDescription = null,
                            tint = UlTheme.colors.errorColor
                        )
                        Text(
                            text = "Error while uploading",
                            style = UlTheme.typography.caption,
                            color = UlTheme.colors.errorColor
                        )
                    }
                }
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(48.dp)
                        .padding(horizontal = 8.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Crossfade(targetState = loadingState.value) {
                        when (it) {
                            EventLoadingState.Error,
                            EventLoadingState.Ready,
                            EventLoadingState.Initial -> Text(text = "Upload")
                            EventLoadingState.Loading -> CircularProgressIndicator()
                        }
                    }
                }
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
    }
}
