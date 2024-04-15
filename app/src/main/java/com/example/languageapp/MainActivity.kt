package com.example.languageapp

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.cardview.widget.CardView
import android.content.Intent
import android.graphics.Color

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.main_activity)

        val cardView: CardView = findViewById(R.id.myProfile)
        cardView.setOnClickListener {
            val intent = Intent(this@MainActivity, ProfileActivity::class.java)
            startActivity(intent)
        }

        window.statusBarColor = Color.parseColor("#410FA3")
        window.navigationBarColor = Color.parseColor("#410FA3")

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val networkManager = NetworkManager(this)
        networkManager.observe(this) { hasNetwork ->
            if (!hasNetwork) {
                val intent = Intent(this, NetworkErrorActivity::class.java)
                startActivity(intent)
            }
        }

        val animals: CardView = findViewById(R.id.animal_exercise)
        animals.setOnClickListener {
            val intent = Intent(this@MainActivity, ExerciseAnimals::class.java)
            startActivity(intent)
        }

        val words: CardView = findViewById(R.id.word_practice)
        words.setOnClickListener {
            val intent = Intent(this@MainActivity, WordPracticeActivity::class.java)
            startActivity(intent)
        }

        val listening: CardView = findViewById(R.id.listening)
        listening.setOnClickListener {
            val intent = Intent(this@MainActivity, Listening::class.java)
            startActivity(intent)
        }
    }
}