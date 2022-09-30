package ru.bmstu.ulife

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavArgument
import androidx.navigation.NavDestination
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import ru.bmstu.ulife.databinding.ContainerMainBinding
import ru.bmstu.ulife.ext.hide
import ru.bmstu.ulife.ext.show

class ContainerMainActivity : AppCompatActivity() {
    private var binding: ContainerMainBinding? = null

    private var navHostFragment: Fragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ContainerMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding?.root)
        navHostFragment = supportFragmentManager.findFragmentById(R.id.containerMain_root)
        init()
    }

    private fun init() {
        binding?.containerMainBottomNavigation?.apply {
            background = null
        }

        navHostFragment = supportFragmentManager.findFragmentById(R.id.containerMain_root)
        navHostFragment?.let { host ->
            binding?.containerMainBottomNavigation?.setupWithNavController(host.findNavController())
            host.findNavController().addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.authorizationFragment
                    -> onHideNavigator()
                    else -> onShowNavigator()
                }
            }
        }
    }

    fun onHideNavigator() {
        binding?.containerMainBottomNavigation?.let { navigation ->
            navigation.hide()
            binding?.bottomAppBar?.hide()
        }
    }

    fun onShowNavigator() {
        binding?.containerMainBottomNavigation?.let { navigation ->
            if (navigation.visibility == View.VISIBLE) {
                return
            }
            navigation.show()
            binding?.bottomAppBar?.show()
        }
    }
}