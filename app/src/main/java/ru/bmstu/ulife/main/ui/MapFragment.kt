package ru.bmstu.ulife.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.App
import ru.bmstu.ulife.ContainerMainActivity
import ru.bmstu.ulife.databinding.FragmentMapBinding
import ru.bmstu.ulife.main.common.ui.MainComposeContent
import ru.bmstu.ulife.main.maps.ui.MapsScreen
import ru.bmstu.ulife.uicommon.theme.MainTheme
import ru.bmstu.ulife.utils.SharedPreferencesStorage

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    private var storage: SharedPreferencesStorage = get(named("storage"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root
        println("LOG:: map id=" + storage.getAuthToken() + " " + storage.getUserId())
        if (storage.getAuthToken() == 0) {
            navigateToAuthorizationFragment()
        } else {
            binding.composeViewMap.apply {
                setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
                setContent {
                    MainTheme {
                        MainComposeContent {
                            val action =
                                MapFragmentDirections.actionMapFragmentToEventDetailFragment(it.eventId)
                            findNavController().navigate(action)
                        }
                    }
                }
            }
        }
        return view
    }

    private fun navigateToAuthorizationFragment() {
        val action =
            MapFragmentDirections.actionMapFragmentToAuthorizationFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}