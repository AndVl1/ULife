package ru.bmstu.ulife.main.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.android.ext.android.get
import org.koin.core.qualifier.named
import ru.bmstu.ulife.databinding.FragmentProfileBinding
import ru.bmstu.ulife.utils.SharedPreferencesStorage

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding

    private var storage: SharedPreferencesStorage = get<SharedPreferencesStorage>(named("storage"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
        }
    }
}