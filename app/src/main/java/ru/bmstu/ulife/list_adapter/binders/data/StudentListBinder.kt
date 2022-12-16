package ru.bmstu.ulife.list_adapter.binders.data

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.StudentModel
import ru.bmstu.ulife.databinding.ItemStudentBinding
import ru.bmstu.ulife.list_adapter.FeedItemViewBinder


class StudentListBinder(
    private val block: (data: StudentModel) -> Unit
) : FeedItemViewBinder<StudentModel, StudentListBinder.VhPromo>(StudentModel::class.java) {

    override fun createViewHolder(parent: ViewGroup): RecyclerView.ViewHolder {
        return VhPromo(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_student, parent, false),
            block
        )
    }

    override fun bindViewHolder(model: StudentModel, viewHolder: VhPromo) {
        viewHolder.bind(model)
    }

    override fun getFeedItemType(): Int = R.layout.item_student

    override fun areItemsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
        return oldItem.Email == newItem.Email
                && oldItem.Name == newItem.Name
                && oldItem.Surname == newItem.Surname
                && oldItem.Telegram == newItem.Telegram
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: StudentModel, newItem: StudentModel): Boolean {
        return oldItem == newItem
    }

    inner class VhPromo(
        val view: View,
        val block: (data: StudentModel) -> Unit,
    ) : RecyclerView.ViewHolder(view) {
        private val binding: ItemStudentBinding = ItemStudentBinding.bind(view)

        @SuppressLint("SetTextI18n")
        fun bind(item: StudentModel) {
            itemView.setOnClickListener { block(item) }

            binding.apply {
                studentEmail.text = item.Email
                studentName.text = item.Name
                studentSurname.text = item.Surname
                studentTelegram.text = item.Telegram
            }
        }
    }
}