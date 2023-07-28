package com.naserkilop.testandriydubrivnyi.ui.guessnumber

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.naserkilop.testandriydubrivnyi.databinding.ActivityGuessNumberBinding

class GuessNumberActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGuessNumberBinding
    private lateinit var viewModel: GuessNumberViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuessNumberBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this).get(GuessNumberViewModel::class.java)
        viewModel.getRandomNumbersFromApi()


        binding.buttonSubmit.setOnClickListener {
            val userGuess = binding.editTextGuess.text.toString().toIntOrNull()
            if (userGuess != null) {
                viewModel.checkGuess(userGuess)
            } else {
                Toast.makeText(this, "Введите число", Toast.LENGTH_SHORT).show()
            }
        }
        binding.buttonReset.setOnClickListener{
            viewModel.restartGame()
        }
    }
}