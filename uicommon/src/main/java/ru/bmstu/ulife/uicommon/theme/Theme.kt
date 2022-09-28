package ru.bmstu.ulife.uicommon.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun MainTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    corners: UlCorners = UlCorners.Rounded,
    textSize: UlSize = UlSize.Medium,
    paddingSize: UlSize = UlSize.Medium,
    style: UlStyle = UlStyle.Green,
    content: @Composable () -> Unit,
) {
    val colors = if (darkTheme) {
        when (style) {
            UlStyle.Green -> greenDarkPalette
            UlStyle.Red -> redDarkPalette
            UlStyle.Purple -> purpleDarkPalette
            UlStyle.Orange -> orangeDarkPalette
            UlStyle.Blue -> blueDarkPalette
        }
    } else {
        when (style) {
            UlStyle.Green -> greenLightPalette
            UlStyle.Red -> redLightPalette
            UlStyle.Purple -> purpleLightPalette
            UlStyle.Orange -> orangeLightPalette
            UlStyle.Blue -> blueLightPalette
        }
    }

    val shapes = UlShape(
        padding = when (paddingSize) {
            UlSize.Small -> 8.dp
            UlSize.Medium -> 12.dp
            UlSize.Big -> 16.dp
        },
        cornerStyle = if (corners == UlCorners.Rounded) {
            RoundedCornerShape(8.dp)
        } else {
            RoundedCornerShape(2.dp)
        }
    )

    val typography = UlTypography(
        heading = TextStyle(
            fontSize = when (textSize) {
                UlSize.Small -> 24.sp
                UlSize.Medium -> 28.sp
                UlSize.Big -> 32.sp
            },
            fontWeight = FontWeight.Bold,
            color = colors.primaryText,
        ),
        body = TextStyle(
            fontSize = when (textSize) {
                UlSize.Small -> 14.sp
                UlSize.Medium -> 16.sp
                UlSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Normal,
            color = colors.primaryText,
        ),
        toolbar = TextStyle(
            fontSize = when (textSize) {
                UlSize.Small -> 14.sp
                UlSize.Medium -> 16.sp
                UlSize.Big -> 18.sp
            },
            fontWeight = FontWeight.Medium,
            color = colors.primaryText,
        ),
        caption = TextStyle(
            fontSize = when (textSize) {
                UlSize.Small -> 10.sp
                UlSize.Medium -> 12.sp
                UlSize.Big -> 14.sp
            },
            color = colors.primaryText,
        ),
        errorToast = TextStyle(
            fontSize = when (textSize) {
                UlSize.Small -> 14.sp
                UlSize.Medium -> 16.sp
                UlSize.Big -> 18.sp
            },
        )
    )

    CompositionLocalProvider(
        LocalUlColors provides colors,
        LocalUlShape provides shapes,
        LocalUlTypography provides typography,
        content = content
    )
}