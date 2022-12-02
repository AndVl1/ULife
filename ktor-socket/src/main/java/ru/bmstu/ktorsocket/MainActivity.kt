package ru.bmstu.ktorsocket

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.SpannableString
import android.text.TextWatcher
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.bmstu.ktorsocket.content.data.PeakViewModel
import ru.bmstu.ktorsocket.databinding.FragmentTestBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: FragmentTestBinding

    private val peakViewModel by viewModel<PeakViewModel>()

    var currentString = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentTestBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        peakViewModel.initSockets()

        lifecycleScope.launch {
            peakViewModel.result.collect {
                if (it.res == "unspecified" || it.res == "error") {
                    binding.outputTv.text = it.res
                } else {
                    val indexOfRedSymbol = it.res.toInt() * 2

                    val text = SpannableString(currentString)
                    text.setSpan(ForegroundColorSpan(Color.RED), indexOfRedSymbol, indexOfRedSymbol + 1, 0)

                    binding.outputTv.setText(text, TextView.BufferType.SPANNABLE)
                }
            }
        }

        binding.inputTv.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                if (s != null && s.toString() != "") {
                    currentString = s.toString()
                    peakViewModel.onStrInput(s.toString())
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }
}