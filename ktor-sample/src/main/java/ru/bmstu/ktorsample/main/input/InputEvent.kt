package ru.bmstu.ktorsample.main.input

sealed class InputEvent {
    data class ButtonClicked(val value: Int): InputEvent()
}
