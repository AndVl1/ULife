package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.models.ShopModel
import ru.bmstu.ulife.databinding.FragmentShopListBinding
import ru.bmstu.ulife.list_adapter.FeedAdapter
import ru.bmstu.ulife.list_adapter.FeedItemBinder
import ru.bmstu.ulife.list_adapter.FeedItemClass
import ru.bmstu.ulife.list_adapter.binders.data.ShopListBinder
import ru.bmstu.ulife.utils.SharedPreferencesStorage

class ShopListFragment : Fragment() {
    private lateinit var binding: FragmentShopListBinding
    private lateinit var adapter: FeedAdapter

    private var list: MutableList<ShopModel> = mutableListOf()
    private val viewBinders = mutableMapOf<FeedItemClass, FeedItemBinder>()

    private var storage: SharedPreferencesStorage = get(named("storage"))

    @Suppress("UNCHECKED_CAST")
    private fun createBinders() {
        val data = ShopListBinder(
            { data -> onEventClick(data) },
            Glide.with(requireView())
        )
        viewBinders[data.modelClass] = data as FeedItemBinder

        adapter = FeedAdapter(viewBinders)

        list = storage.getShopList()
        /*list.add(
            ShopModel(
                "Москва...",
                "ТЦ Атриум",
                "Метро Курская",
                "+79171234567",
                55.7,
                37.6,
                "https://sun9-32.userapi.com/impf/AtLcOt4w-WQ5j-_Mak2rN4x0v0CXA2OSs66fKg/BhQLK37Mdrs.jpg?size=1000x667&quality=96&sign=90471f3aaccaf02d193d5cb8bb9845b6&type=album",
            )
        )*/
        adapter.submitList(list as List<Any>?)
    }

    private fun onEventClick(data: ShopModel) {
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentShopListBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createBinders()
        init()
        binding.shopListRv.adapter = adapter
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun init() {
        binding.addShopBtn.setOnClickListener {
            list.add(
                ShopModel(
                    binding.createShopAddress.text.toString(),
                    binding.createShopTitle.text.toString(),
                    binding.createShopMetro.text.toString(),
                    binding.createShopPhone.text.toString(),
                    binding.createShopLat.text.toString().toDouble(),
                    binding.createShopLong.text.toString().toDouble(),
                    binding.createShopPhoto.text.toString()
                )
            )
            adapter.submitList(list as List<Any>?)
            adapter.notifyDataSetChanged()

            storage.putShopList(list)
        }
    }
}