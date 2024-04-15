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

class ExerciseAnimasFail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.exercise_animas_fail_activity)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        window.statusBarColor = Color.parseColor("#D6185D")
        window.navigationBarColor = Color.parseColor("#D6185D")


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

        val next = findViewById<Button>(R.id.nextButton)
        next.setOnClickListener {
            val intent = Intent(this@ExerciseAnimasFail, MainActivity::class.java)
            startActivity(intent)
        }

        val tryAgain = findViewById<Button>(R.id.tryButton)
        tryAgain.setOnClickListener {
            val intent = Intent(this@ExerciseAnimasFail, ExerciseAnimals::class.java)
            startActivity(intent)
        }
    }
}