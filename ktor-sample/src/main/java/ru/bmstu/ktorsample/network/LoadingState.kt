package ru.bmstu.ktorsample.network

sealed class LoadingState {
    object Loading : LoadingState()
    data class Loaded(val data: EvenResponse.Success) : LoadingState()
    object Error : LoadingState()
    object Initial : LoadingState()
}
