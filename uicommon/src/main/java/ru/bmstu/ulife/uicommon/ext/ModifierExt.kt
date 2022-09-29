package ru.bmstu.ulife.uicommon.ext

import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

fun Modifier.conditional(apply: Boolean, modifier : Modifier.() -> Modifier): Modifier {
    return if (!apply) {
        this
    } else {
        then(modifier(this))
    }
}
