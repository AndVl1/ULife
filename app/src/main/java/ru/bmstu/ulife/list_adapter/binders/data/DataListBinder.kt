package ru.bmstu.ulife.list_adapter.binders.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.DataModel
import ru.bmstu.ulife.databinding.ItemDataBinding
import ru.bmstu.ulife.list_adapter.FeedItemViewBinder

class DataListBinder(
    private val block: (data: DataModel) -> Unit
) : FeedItemViewBinder<DataModel, DataListBinder.VhPromo>(DataModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return VhPromo(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_data, parent, false),
            block
        )
    }

    override fun bindViewHolder(model: DataModel, viewHolder: VhPromo) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.item_data

    override fun areItemsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem.name == newItem.name
                && oldItem.ID == newItem.ID
                && oldItem.deaths == newItem.deaths
                && oldItem.location == newItem.location
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DataModel, newItem: DataModel): Boolean {
        return oldItem == newItem
    }

    inner class VhPromo(
        val view: View,
        val block: (data: DataModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding: ItemDataBinding = ItemDataBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(item: DataModel) {
            itemView.setOnClickListener { block(item) }

            binding.apply {
                dataId.text = item.ID.toString()
                dataDeaths.text = item.deaths.toString()
                dataLocation.text = item.location
                dataYear.text = item.year.toString()
                dataName.text = item.name
            }
        }
    }
}