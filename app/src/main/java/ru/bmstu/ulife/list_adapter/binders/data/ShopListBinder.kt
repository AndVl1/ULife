package ru.bmstu.ulife.list_adapter.binders.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.models.ShopModel
import ru.bmstu.ulife.databinding.ItemShopBinding
import ru.bmstu.ulife.list_adapter.FeedItemViewBinder

class ShopListBinder(
    private val block: (data: ShopModel) -> Unit,
    private val requestManager: RequestManager,
) : FeedItemViewBinder<ShopModel, ShopListBinder.VhPromo>(ShopModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return VhPromo(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_shop, parent, false),
            block
        )
    }

    override fun bindViewHolder(model: ShopModel, viewHolder: VhPromo) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.item_event

    override fun areItemsTheSame(oldItem: ShopModel, newItem: ShopModel): Boolean {
        return oldItem.metro == newItem.metro
                && oldItem.phone == newItem.phone
                && oldItem.url == newItem.url
                && oldItem.title == newItem.title
                && oldItem.address == newItem.address
                && oldItem.latitude == newItem.latitude
                && oldItem.longitude == newItem.longitude
    }

    override fun areContentsTheSame(oldItem: ShopModel, newItem: ShopModel): Boolean {
        return oldItem == newItem
    }

    inner class VhPromo(
        val view: View,
        val block: (data: ShopModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding: ItemShopBinding = ItemShopBinding.bind(view)
        private val logoHolder =
            ContextCompat.getDrawable(view.context, R.drawable.empty_cover)

        @SuppressLint("SetTextI18n")
        fun bind(item: ShopModel) {
            itemView.setOnClickListener { block(item) }

            binding.apply {
                shopTitle.text = item.title
                shopAddress.text = item.address
                shopMetro.text = item.metro
                shopPhone.text = item.phone
                shopCoords.text =item.latitude.toString() + ", " + item.longitude.toString()
                requestManager.load(item.url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .error(logoHolder)
                    .override(320)
                    .into(shopImage)
            }
        }
    }
}