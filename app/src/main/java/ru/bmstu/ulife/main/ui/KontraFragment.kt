package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.models.DataModel
import ru.bmstu.ulife.data.models.KrResponse
import ru.bmstu.ulife.data.models.ShopModel
import ru.bmstu.ulife.data.states.EventDetailState
import ru.bmstu.ulife.data.states.LoginState
import ru.bmstu.ulife.databinding.FragmentKontraBinding
import ru.bmstu.ulife.databinding.FragmentProfileBinding
import ru.bmstu.ulife.databinding.FragmentShopListBinding
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.list_adapter.FeedAdapter
import ru.bmstu.ulife.list_adapter.FeedItemBinder
import ru.bmstu.ulife.list_adapter.FeedItemClass
import ru.bmstu.ulife.list_adapter.binders.data.DataListBinder
import ru.bmstu.ulife.list_adapter.binders.data.ShopListBinder
import ru.bmstu.ulife.utils.SharedPreferencesStorage
import ru.bmstu.ulife.view_models.EventViewModel
import ru.bmstu.ulife.view_models.LoginViewModel


class KontraFragment : Fragment() {
    private lateinit var binding: FragmentKontraBinding
    private lateinit var adapter: FeedAdapter

    private val eventViewModel by viewModel<EventViewModel>()
    private val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

    @Suppress("UNCHECKED_CAST")
    private fun createBinders() {
        val data = DataListBinder { data -> onDataClick(data) }
        viewBinders[data.modelClass] = data as FeedItemBinder
        adapter = FeedAdapter(viewBinders)
    }

    private fun onDataClick(data: DataModel) {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentKontraBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createBinders()
        binding.studentListRv.adapter = adapter

        lifecycleScope.launch {
            eventViewModel.getState().collect { onDataChanged(it) }
        }

        eventViewModel.getData()
    }

    private fun onDataChanged(newState: EventDetailState) {
        when (newState) {
            is EventDetailState.DataSuccess -> {
                if (newState.krResponse is KrResponse.Success) {
                    showData(newState.krResponse.resp)
                }
            }
            is EventDetailState.Error -> onError(newState.textId)
            else -> {}
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun showData(listData: List<DataModel>) {
        adapter.submitList(listData)
        adapter.notifyDataSetChanged()
    }

    private fun onError(textId: Int) {
        showSnackbar(
            binding.root,
            getString(textId),
            duration = 1500
        )
    }
}