package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.models.EventModel
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.states.EventDetailState
import ru.bmstu.ulife.databinding.FragmentEventDetailBinding
import ru.bmstu.ulife.ext.TimeUtils.getDateTimeFromLongTimestamp
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.list_adapter.FeedAdapter
import ru.bmstu.ulife.list_adapter.FeedItemBinder
import ru.bmstu.ulife.list_adapter.FeedItemClass
import ru.bmstu.ulife.list_adapter.binders.data.MemberListBinder
import ru.bmstu.ulife.utils.SharedPreferencesStorage
import ru.bmstu.ulife.view_models.EventViewModel

class EventDetailFragment : Fragment() {
    private lateinit var binding: FragmentEventDetailBinding

    private val eventViewModel by viewModel<EventViewModel>()

    private var storage: SharedPreferencesStorage = get(named("storage"))

    val args: EventDetailFragmentArgs by navArgs()

    private lateinit var adapter: FeedAdapter

    private val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            eventViewModel.getState().collect { onEventDetailStateChanged(it) }
        }

        createBinders()
        binding.rvMemberList.adapter = adapter

        eventViewModel.getEventByEventId(storage.getUserId().toString(), args.eventId)
    }

    private fun onEventDetailStateChanged(newState: EventDetailState) {
        when (newState) {
            is EventDetailState.EventSuccess -> {
                showEventDetail(newState.eventModel)
            }
            is EventDetailState.Error -> onError(newState.textId)
            else -> {}
        }
    }

    private fun showEventDetail(eventModel: EventModel) {
        binding.apply {
            title.text = eventModel.title
            categoryTitle.text = eventModel.categoryTitle
            address.text = eventModel.address
            eventDescription.text = eventModel.description
            eventDetailTimeStartText.text = "Начало: " + getDateTimeFromLongTimestamp(eventModel.timeStart!!)
            eventDetailTimeEndText.text = "Конец: " + getDateTimeFromLongTimestamp(eventModel.timeEnd!!)
            Glide.with(requireView()).load(eventModel.eventAvatar)
                .transition(DrawableTransitionOptions.withCrossFade())
                .override(320)
                .into(eventImage)
        }
    }

    private fun onError(textId: Int) {
        showSnackbar(
            binding.root,
            getString(textId),
            duration = 1500
        )
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