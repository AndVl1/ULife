package ru.bmstu.ulife.list_adapter.binders.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.databinding.ItemEventBinding
import ru.bmstu.ulife.ext.TimeUtils.getDateTimeFromTimestamp
import ru.bmstu.ulife.list_adapter.FeedItemViewBinder

class EventListBinder(
    private val block: (data: EventModel) -> Unit,
    private val requestManager: RequestManager,
) : FeedItemViewBinder<EventModel, EventListBinder.VhPromo>(EventModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return VhPromo(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_event, parent, false),
            block
        )
    }

    override fun bindViewHolder(model: EventModel, viewHolder: VhPromo) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.item_event

    override fun areItemsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return oldItem.authorId == newItem.authorId
                && oldItem.eventId == newItem.eventId
                && oldItem.eventAvatar == newItem.eventAvatar
                && oldItem.title == newItem.title
                && oldItem.address == newItem.address
                && oldItem.categoryTitle == newItem.categoryTitle
                && oldItem.address == newItem.address
                && oldItem.description == newItem.description
                && oldItem.categoryTitle == newItem.categoryTitle
    }

    override fun areContentsTheSame(oldItem: EventModel, newItem: EventModel): Boolean {
        return oldItem == newItem
    }

    inner class VhPromo(
        val view: View,
        val block: (data: EventModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding: ItemEventBinding = ItemEventBinding.bind(view)
        private val logoHolder =
            ContextCompat.getDrawable(view.context, R.drawable.empty_cover)

        fun bind(item: EventModel) {
            itemView.setOnClickListener { block(item) }

            binding.apply {
                eventTitle.text = item.title
                eventAddress.text = item.address
                eventCategoryTitle.text = item.categoryTitle
                eventDuration.text = getEventDuration(item.timeStart, item.timeEnd)
                requestManager.load(item.eventAvatar)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(logoHolder)
                    .override(320)
                    .into(eventImage)
            }
        }
    }

    private fun getEventDuration(timestart: String, timeend: String): CharSequence {
        return getDateTimeFromTimestamp(timestart) + " - " + getDateTimeFromTimestamp(timeend)
    }
}