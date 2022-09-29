package ru.bmstu.ulife.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import ru.bmstu.ulife.databinding.FragmentMapBinding
import ru.bmstu.ulife.main.common.ui.MainComposeContent
import ru.bmstu.ulife.main.events.maps.ui.MapsScreen
import ru.bmstu.ulife.uicommon.theme.MainTheme

class MapFragment : Fragment() {

    private var _binding: FragmentMapBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapBinding.inflate(inflater, container, false)
        val view = binding.root
        binding.composeViewMap.apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                MainTheme(darkTheme = false) {
                    MainComposeContent (onEventDetailsClicked = {
                        val action =
                            MapFragmentDirections.actionMapFragmentToEventDetailFragment(it.eventId)
                        findNavController().navigate(action)
                    })
                }
            }
        }
        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}