package ru.bmstu.ulife.main.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.ContainerMainActivity
import ru.bmstu.ulife.data.models.FirebaseUserModel
import ru.bmstu.ulife.databinding.FragmentAuthLabBinding
import ru.bmstu.ulife.ext.showSnackbar
import ru.bmstu.ulife.utils.SharedPreferencesStorage


class AuthLabFragment : Fragment() {
    private lateinit var auth: FirebaseAuth

    private var binding: FragmentAuthLabBinding? = null

    private var storage: SharedPreferencesStorage = get(named("storage"))

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAuthLabBinding.inflate(
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
        auth = Firebase.auth

        if (storage.getIsLogout()) {
            auth.signOut()
            Toast.makeText(context, "Sign out successful.", Toast.LENGTH_SHORT).show()
            storage.putIsLogout(false)
        }
    }

    override fun onStart() {
        super.onStart()
        val currentUser = auth.currentUser
        if(currentUser != null) {
            reload()
        }
    }

    private fun reload() {
        auth.currentUser!!.reload().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                updateUI(auth.currentUser)
                Toast.makeText(context, "Reload successful!", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Failed to reload user.", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun setupClickListeners() {
        binding?.apply {
            signInBtn.setOnClickListener {
                val email = signInEtEmail.text.toString()
                val password = signInEtPassword.text.toString()
                signIn(email, password)
            }
            signUpBtn.setOnClickListener {
                val email = signInEtEmail.text.toString()
                val password = signInEtPassword.text.toString()
                createAccount(email, password)
            }
        }
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(context, "Create account successful!", Toast.LENGTH_SHORT).show()
                updateUI(user)
            } else {
                println("Error with $email and ${password}: " + task.exception)
                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val user = auth.currentUser
                Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                updateUI(user)
            } else {
                println("Error: " + task.exception)
                Toast.makeText(requireContext(), "Authentication failed.", Toast.LENGTH_SHORT).show()
                updateUI(null)
            }
        }
    }

    private fun updateUI(user: FirebaseUser?) {
        if(user != null) {
            user.email?.let { storage.putUserEmail(it) }
            val action = AuthLabFragmentDirections.actionAuthLabFragmentToStudentListFragment()
            findNavController().navigate(action)
        }
    }
}