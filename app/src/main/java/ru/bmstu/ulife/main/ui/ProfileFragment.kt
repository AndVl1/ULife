package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.states.LoginState
import ru.bmstu.ulife.databinding.FragmentProfileBinding
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.utils.SharedPreferencesStorage
import ru.bmstu.ulife.view_models.LoginViewModel

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private val loginViewModel by viewModel<LoginViewModel>()

    private var storage: SharedPreferencesStorage = get<SharedPreferencesStorage>(named("storage"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        lifecycleScope.launch {
            loginViewModel.getState().collect { onLoginStateChanged(it) }
        }
    }

    private fun onLoginStateChanged(newState: LoginState) {
        when (newState) {
            is LoginState.LogoutSuccess -> {
                logout()

            }
            is LoginState.Error -> onError(newState.textId)
            else -> { }
        }
    }

    private fun onError(textId: Int) {
        showSnackbar(
            binding.root,
            getString(textId),
            duration = 1500
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(
            inflater,
            container,
            false
        )
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    fun init() {
        binding.apply {
            settingsHeaderTvUsername.text = storage.getUserFirstName()
            settingsHeaderTvEmail.text = storage.getUserEmail()

            settingsTvFullName.text = storage.getUserFirstName() + " " + storage.getUserSecondName()
            settingsTvEmail.text = storage.getUserEmail()
            settingsTvBirthday.text = storage.getUserAge().toString()
            settingsTvGender.text = storage.getUserGender()
            settingsTvAddress.text = storage.getUserCountry()
            settingsTvCity.text = storage.getUserCity()

            logoutButton.setOnClickListener {
                loginViewModel.logout(storage.getUserId())
            }
        }
    }

    private fun logout() {
        storage.removeAuthToken()
        navigateToAuthorizationFragment()
    }

    private fun navigateToAuthorizationFragment() {
        val action =
            ProfileFragmentDirections.actionProfileFragmentToAuthorizationFragment()
        findNavController().navigate(action)
    }
}