package ru.bmstu.ulife.uicommon.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp

data class UlColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryText: Color,
    val secondaryBackground: Color,
    val tintColor: Color,
    val controlColor: Color,
    val errorColor: Color,
)

data class UlShape(
    val padding: Dp,
    val cornerStyle: Shape
)

data class UlTypography(
    val heading: TextStyle,
    val body: TextStyle,
    val toolbar: TextStyle,
    val caption: TextStyle
)

object UlTheme {
    val colors: UlColors
        @Composable
        get() = LocalUlColors.current

    val shape: UlShape
        @Composable
        get() = LocalUlShape.current

    val typography: UlTypography
        @Composable
        get() = LocalUlTypography.current
}

enum class UlCorners {
    Flat, Rounded
}

enum class UlSize {
    Small, Medium, Big
}

enum class UlStyle {
    Red, Blue, Green, Purple, Orange
}

enum class UlAnimations

val LocalUlColors = staticCompositionLocalOf<UlColors> {
    error("No colors provided")
}

val LocalUlShape = staticCompositionLocalOf<UlShape> {
    error("No shape provided")
}

val LocalUlTypography = staticCompositionLocalOf<UlTypography> {
    error("No typography provided")
}