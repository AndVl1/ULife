package ru.bmstu.ulife.ext

import android.view.View
import androidx.annotation.Keep
import com.google.android.material.snackbar.Snackbar

@Keep
fun showSnackbar(
    root: View,
    text: String,
    actionText: String? = null,
    onClickListener: View.OnClickListener? = null,
    duration: Int = 1000
): Snackbar {
    val snackbar = Snackbar.make(root, text, duration)
    snackbar.setAction(actionText, onClickListener)
    snackbar.show()
    return snackbar

}