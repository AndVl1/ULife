package ru.bmstu.ulife.list_adapter

import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

typealias FeedItemClass = Class<out Any>
typealias FeedItemBinder = FeedItemViewBinder<Any, RecyclerView.ViewHolder>

internal class FeedDiffCallback(
        private val viewBinders: Map<FeedItemClass, FeedItemBinder>
) : DiffUtil.ItemCallback<Any>() {

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        if (oldItem::class != newItem::class) {
            return false
        }
        return viewBinders[oldItem::class.java]?.areItemsTheSame(oldItem, newItem) ?: false
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return viewBinders[oldItem::class.java]?.areContentsTheSame(oldItem, newItem) ?: false
    }
}