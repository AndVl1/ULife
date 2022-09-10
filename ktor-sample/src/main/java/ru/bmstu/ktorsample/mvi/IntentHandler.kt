package ru.bmstu.ktorsample.mvi

interface IntentHandler<T> {
    fun handleEvent(event: T)
}
