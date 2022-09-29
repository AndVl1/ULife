package ru.bmstu.ulife.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.androidx.viewmodel.ext.android.getViewModelFactory
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.states.LoginState
import ru.bmstu.ulife.databinding.FragmentAuthorizationBinding
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.view_models.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.module._singleInstanceFactory
import org.koin.core.qualifier.named
import ru.bmstu.ulife.data.models.SendToServerUserModel
import ru.bmstu.ulife.utils.SharedPreferencesStorage


class AuthorizationFragment : Fragment() {
    private val loginViewModel by viewModel<LoginViewModel>()

    private var userModel: UserModel? = null

    private var binding: FragmentAuthorizationBinding? = null

    private var storage: SharedPreferencesStorage = get<SharedPreferencesStorage>(named("storage"))

    var isRegisterScreen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthorizationBinding.inflate(
            inflater,
            container,
            false
        )
        return binding?.root
    }

    override fun onDestroyView() {
        binding = null
        super.onDestroyView()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupClickListeners()
        lifecycleScope.launch {
            loginViewModel.getState().collect { onLoginStateChanged(it) }
        }
    }

    private fun onLoginStateChanged(newState: LoginState) {
        when (newState) {
            is LoginState.LoginSuccess -> {
                val userWithTokenModel = newState.userWithTokenModel
                storage.putAuthToken(userWithTokenModel.token)
                storage.putUserWithTokenModel(userWithTokenModel)
                onAuthorizationClick()
            }
            is LoginState.RegisterSuccess -> {
                if (storage.getUserEmail() != null && storage.getUserPassword() != null) {
                    loginViewModel.login(
                        storage.getUserEmail().toString(),
                        storage.getUserPassword().toString()
                    )
                }
            }
            is LoginState.Error -> onError(newState.textId)
            else -> {}
        }
    }

    private fun informUser(textId: Int) {
        binding?.let {
            showSnackbar(
                it.root,
                getString(textId),
                duration = 1500
            )
        }
    }

    private fun onError(textId: Int) {
        binding?.let {
            showSnackbar(
                it.root,
                getString(textId),
                duration = 1500
            )
        }
    }

    private fun setupClickListeners() {
        binding?.apply {
            signInBtnActionContinue.setOnClickListener {
                if (isRegisterScreen) {
                    registerUser()
                } else {
                    val login = signInEtEmail.text.toString()
                    val password = signInEtPassword.text.toString()
                    loginUser(login, password)
                }
            }
            signInBtnSignIn.setOnClickListener {
                isRegisterScreen = !isRegisterScreen
                if (isRegisterScreen) {
                    setRegisterView()
                } else {
                    setLoginView()
                }
            }
            closeBtn.setOnClickListener { findNavController().navigateUp() }
        }
    }

    private fun setRegisterView() {
        binding?.apply {
            textHasAccount.text = getString(R.string.sign_in_already_has_account)
            signInBtnSignIn.text = getString(R.string.action_sign_in)
            signInTilFirstName.visibility = View.VISIBLE
            signUpTilGender.visibility = View.GONE
            signInTilSecondName.visibility = View.VISIBLE
            signUpTilAge.visibility = View.VISIBLE
            signUpTilCity.visibility = View.VISIBLE
            signUpTilCountry.visibility = View.VISIBLE
        }
    }

    private fun setLoginView() {
        binding?.apply {
            textHasAccount.text = getString(R.string.sign_in_hasnt_account)
            signInBtnSignIn.text = getString(R.string.action_sign_up)
            signInTilFirstName.visibility = View.GONE
            signUpTilGender.visibility = View.GONE
            signInTilSecondName.visibility = View.GONE
            signUpTilAge.visibility = View.GONE
            signUpTilCity.visibility = View.GONE
            signUpTilCountry.visibility = View.GONE
        }
    }

    private fun loginUser(login: String, password: String) {
        loginViewModel.login(login, password)
    }

    private fun registerUser() {
        binding?.apply {
            val userModel = SendToServerUserModel(
                firstName = signInEtFirstName.text.toString(),
                lastName = signInEtSecondName.text.toString(),
                email = signInEtEmail.text.toString(),
                age = signUpEtAge.text.toString().toInt(),
                gender = signUpEtGender.text.toString(),
                country = signUpEtCountry.text.toString(),
                city = signUpEtCity.text.toString(),
                password = signInEtPassword.text.toString(),
                roleId = 0
            )
            loginViewModel.register(userModel)
        }
    }

    private fun onAuthorizationClick() {
        val action =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToMapFragment()
        findNavController().navigate(action)
    }
}