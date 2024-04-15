package com.example.languageapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class ExerciseAnimals : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.exercise_animals_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#410FA3")
        window.navigationBarColor = Color.parseColor("#410FA3")

        val backButton = findViewById<ImageButton>(R.id.page_back)
        backButton.setOnClickListener {
            finish()
        }

        val networkManager = NetworkManager(this)
        networkManager.observe(this) { hasNetwork ->
            if (!hasNetwork) {
                val intent = Intent(this, NetworkErrorActivity::class.java)
                startActivity(intent)
            }
        }

        val check = findViewById<Button>(R.id.checkButton)
        check.setOnClickListener {
            val answerInput = findViewById<TextInputEditText>(R.id.answerInput)
            val answer = answerInput.text.toString().lowercase()
            val intent = when (answer) {
                "racoon" -> Intent(this, ExerciseAnimalsSuccess::class.java)
                else -> Intent(this, ExerciseAnimasFail::class.java)
            }
            startActivity(intent)
        }
    }
}