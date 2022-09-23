package ru.bmstu.ulife.list_adapter
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView

class FeedAdapter(
        private val viewBinders: Map<FeedItemClass, FeedItemBinder>
) : ListAdapter<Any, RecyclerView.ViewHolder>(FeedDiffCallback(viewBinders)) {

    private val viewTypeToBinder = viewBinders.mapKeys { entry -> entry.value.getFeedItemType() }

    private fun getViewBinder(viewType: Int): FeedItemBinder = viewTypeToBinder.getValue(viewType)

    override fun getItemViewType(position: Int): Int {
        return viewBinders.getValue(super.getItem(position).javaClass).getFeedItemType()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return getViewBinder(viewType).createViewHolder(parent)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        getViewBinder(getItemViewType(position)).bindViewHolder(getItem(position), holder)
    }

    override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
        super.onViewRecycled(holder)
        getViewBinder(holder.itemViewType).onViewRecycled(holder)
    }

    override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
        super.onViewDetachedFromWindow(holder)
        getViewBinder(holder.itemViewType).onViewDetachedFromWindow(holder)
    }
}
