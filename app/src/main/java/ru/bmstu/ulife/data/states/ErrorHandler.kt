package ru.bmstu.ulife.data.states

sealed class ErrorHandler : Exception() {
    object AuthorizationError : ErrorHandler()
    object AccessForbiddenError : ErrorHandler()
    object ResourceNotFoundError : ErrorHandler()
}