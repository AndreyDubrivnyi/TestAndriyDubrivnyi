package com.naserkilop.testandriydubrivnyi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.naserkilop.testandriydubrivnyi.databinding.ActivityMainBinding
import com.naserkilop.testandriydubrivnyi.ui.guessnumber.GuessNumberActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnBeginPlay.setOnClickListener{
            val intent = Intent(this, GuessNumberActivity::class.java)
            startActivity(intent)
        }
    }
}