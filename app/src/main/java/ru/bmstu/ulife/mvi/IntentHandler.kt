package ru.bmstu.ulife.mvi

interface IntentHandler<T> {
    fun handleEvent(event: T)
}
