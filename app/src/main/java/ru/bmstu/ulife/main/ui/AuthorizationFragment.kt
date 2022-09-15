package ru.bmstu.ulife.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.bmstu.ulife.R
import ru.bmstu.ulife.data.models.UserModel
import ru.bmstu.ulife.data.states.LoginState
import ru.bmstu.ulife.databinding.FragmentAuthorizationBinding
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.view_models.LoginViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.bmstu.ulife.data.models.SendToServerUserModel


class AuthorizationFragment : Fragment()  {
    private val loginViewModel by viewModel<LoginViewModel>()

    private var userModel: UserModel? = null

    private var binding: FragmentAuthorizationBinding? = null

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
                val token = newState.token
                println("LOG:: token=" + token)
                onAuthorizationClick()
                //storage.putAuthToken(token)
            }
            is LoginState.RegisterSuccess -> {
                userModel = newState.userModel
                println("LOG:: usermodel=" + userModel)
                onAuthorizationClick()
                //storage.putUserModel(userModel)
            }
            is LoginState.LogoutSuccess -> {
                println("LOG:: logout")
                //storage.removeAuthToken()

            }
            is LoginState.Error -> onError(newState.textId)
            else -> { }
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
                    loginUser()
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
            signInTilSecondName.visibility = View.GONE
            signUpTilAge.visibility = View.GONE
            signUpTilCity.visibility = View.GONE
            signUpTilCountry.visibility = View.GONE
        }
    }

    private fun loginUser() {
        if (userModel != null) {
            loginViewModel.login(userModel!!.userId)
        }
        //onAuthorizationClick()
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
                role = "USER"
            )
            loginViewModel.register(userModel)
        }
        //onAuthorizationClick()
    }

    private fun onAuthorizationClick() {
        val action =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToMapFragment()
        findNavController().navigate(action)
    }
}