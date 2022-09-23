package ru.bmstu.ulife.list_adapter.binders.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.databinding.EventMemberItemBinding
import ru.bmstu.ulife.list_adapter.FeedItemViewBinder

class MemberListBinder(
    private val block: (data: UserModel) -> Unit,
    private val requestManager: RequestManager,
) : FeedItemViewBinder<UserModel, MemberListBinder.VhPromo>(UserModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return VhPromo(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.event_member_item, parent, false),
            block
        )
    }

    override fun bindViewHolder(model: UserModel, viewHolder: VhPromo) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.event_member_item

    override fun areItemsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem.email == newItem.email
    }

    override fun areContentsTheSame(oldItem: UserModel, newItem: UserModel): Boolean {
        return oldItem == newItem
    }

    inner class VhPromo(
        val view: View,
        val block: (data: UserModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding: EventMemberItemBinding = EventMemberItemBinding.bind(view)
        private val logoHolder =
            ContextCompat.getDrawable(view.context, R.drawable.empty_cover)

        fun bind(item: UserModel) {
            itemView.setOnClickListener { block(item) }
            binding.apply {
                eventMemberTvName.text = item.firstName + " " + item.lastName
                eventMemberTvEmail.text = item.email
            }
        }
    }
}