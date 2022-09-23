package ru.bmstu.ulife.main.create.model

sealed class EventLoadingState {
    object Initial : EventLoadingState()
    data class Loading(val progress: Float) : EventLoadingState()
    data class Error(val msg: String) : EventLoadingState()
    object Ready : EventLoadingState()
}

sealed class ImageLoadingState {
    data class Success(val data: ImageLoadingResponse) : ImageLoadingState()
    data class Error(val code: Int, val msg: String) : ImageLoadingState()
}
