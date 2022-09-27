package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.databinding.FragmentEventDetailBinding
import ru.bmstu.ulife.ext.TimeUtils.getDateTimeFromTimestamp
import ru.bmstu.ulife.list_adapter.FeedAdapter
import ru.bmstu.ulife.list_adapter.FeedItemBinder
import ru.bmstu.ulife.list_adapter.FeedItemClass
import ru.bmstu.ulife.list_adapter.binders.data.MemberListBinder
import ru.bmstu.ulife.main.maps.model.EventModel

class EventDetailFragment : Fragment() {
    private lateinit var binding: FragmentEventDetailBinding

    private lateinit var adapter: FeedAdapter

    private val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val eventModel = EventModel("Москва, парк Измайловский", 42, "Спортивное мероприятие", "В воскресенье проводится первый марафон для жителей общежития МГТУ им. Баумана", "https://sun9-32.userapi.com/impf/AtLcOt4w-WQ5j-_Mak2rN4x0v0CXA2OSs66fKg/BhQLK37Mdrs.jpg?size=1000x667&quality=96&sign=90471f3aaccaf02d193d5cb8bb9845b6&type=album",
            1,
            55.7, 37.6,0, 0, "Марафон для бегунов")

        binding.apply {
            title.text = eventModel.title
            categoryTitle.text = eventModel.categoryTitle
            address.text = eventModel.address
            eventDescription.text = eventModel.description
            //eventDetailTimeStartText.text = "Начало: " + getDateTimeFromTimestamp(eventModel.timeStart)
            //eventDetailTimeEndText.text = "Конец: " + getDateTimeFromTimestamp(eventModel.timeEnd)
            Glide.with(requireView()).load(eventModel.eventAvatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .override(320)
                .into(eventImage)
        }
        createBinders()
        binding.rvMemberList.adapter = adapter
    }

    @Suppress("UNCHECKED_CAST")
    private fun createBinders() {
        val data = MemberListBinder(
            { data -> onMemberClick(data) },
            Glide.with(requireView())
        )
        viewBinders[data.modelClass] = data as FeedItemBinder

        adapter = FeedAdapter(viewBinders)

        //val userModel = UserModel("Юлия", "Костюнина", "yulia2012394@gmail.com", 21, "Female", "Россия", "Москва", "161026", "USER", 123456)
        //adapter.submitList(listOf(userModel) as List<Any>?)
    }

    private fun onMemberClick(data: UserModel) {

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEventDetailBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }
}