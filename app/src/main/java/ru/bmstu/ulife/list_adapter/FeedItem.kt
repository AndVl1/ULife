package ru.bmstu.ulife.list_adapter

import androidx.annotation.StringRes

sealed class FeedItem<out T : Any> {
    object LoadingMore : FeedItem<Nothing>()
    object Loading : FeedItem<Nothing>()
    object Default : FeedItem<Nothing>()
    object Unauthorized : FeedItem<Nothing>()
    data class NoMore(@StringRes val resId: Int) : FeedItem<Nothing>()
    data class Empty(@StringRes val resId: Int) : FeedItem<Nothing>()
    data class Error(@StringRes val resId: Int) : FeedItem<Nothing>()
    data class MoreError(@StringRes val resId: Int) : FeedItem<Nothing>()
    data class Inform(@StringRes val resId: Int) : FeedItem<Nothing>()
    data class Success<out T : Any>(val data: T) : FeedItem<T>()
    data class Update(val position: Int) : FeedItem<Nothing>()
    data class Remove(val position: Int) : FeedItem<Nothing>()
}
