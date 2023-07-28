package com.naserkilop.testandriydubrivnyi.ui.guessnumber

import android.app.Application
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.naserkilop.testandriydubrivnyi.data.GameData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class GuessNumberViewModel(application: Application) : AndroidViewModel(application) {

    private val appContext: Context = application.applicationContext
    private var gameData = GameData()
    private var healt = 0
    fun checkGuess(userGuess: Int) {
        if (gameData.secretNumber == userGuess) {
            showToast("Ви виграли!")
            restartGame()
        } else {
            healt++
            if (healt != 5) {
                if (gameData.secretNumber > userGuess) {
                    showToast("Занадто мале число")
                } else {
                    showToast("Занадто велике число число")
                }
            } else {
                showToast("Ви програли. Спробуйте ще раз")
            }
        }
    }

    fun restartGame() {
        healt = 0
        getRandomNumbersFromApi()
    }

    fun getRandomNumbersFromApi() {
        val url =
            "https://www.random.org/integers/?num=1&min=1&max=6&col=1&base=10&format=plain&rnd=new"

        viewModelScope.launch(Dispatchers.IO) {
            try {
                val client = OkHttpClient()
                val request = Request.Builder()
                    .url(url)
                    .build()

                val response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    val randomNumber = response.body?.string()?.trim()?.toIntOrNull()
                    withContext(Dispatchers.Main) {
                        if (randomNumber != null) {
                            gameData.secretNumber = randomNumber
                            Log.d("MY_TAG", ": $randomNumber")
                            Log.d("MY_TAG", "number: ${gameData.secretNumber}")
                        } else {
                            Log.d("MY_TAG", "Failed to parse the random number.")
                            println("Failed to parse the random number.")
                        }
                    }
                } else {
                    withContext(Dispatchers.Main) {
                        Log.d("MY_TAG", "Request failed: ${response.code}")
                    }
                }
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    e.printStackTrace()
                }
            }
        }
    }

    private fun showToast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }
}