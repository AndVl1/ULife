package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.yandex.mapkit.Animation
import com.yandex.mapkit.MapKitFactory
import com.yandex.mapkit.geometry.Point
import com.yandex.mapkit.map.CameraPosition
import com.yandex.mapkit.mapview.MapView
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.ContainerMainActivity
import ru.bmstu.ulife.databinding.FragmentYaMapBinding
import ru.bmstu.ulife.utils.SharedPreferencesStorage


class YaMapFragment : Fragment() {
    private lateinit var binding: FragmentYaMapBinding

    private var storage: SharedPreferencesStorage = get(named("storage"))

    private var mapView: MapView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MapKitFactory.initialize(requireContext())
    }

    override fun onStop() {
        mapView?.onStop()
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
        mapView?.onStart()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentYaMapBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun init() {
        mapView = binding.mapview
        mapView?.map?.move(
            CameraPosition(Point(55.751574, 37.573856), 11.0f, 0.0f, 0.0f),
            Animation(Animation.Type.SMOOTH, 0F),
            null
        )

        storage.getShopList().forEach {
            mapView?.map?.mapObjects?.addPlacemark(Point(it.latitude!!, it.longitude!!))
        }

        (requireActivity() as ContainerMainActivity).onShowNavigator()
    }
}