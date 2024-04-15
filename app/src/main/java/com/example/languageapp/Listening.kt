package com.example.languageapp

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.Locale

class Listening : AppCompatActivity() {
    private val RQ_SPEECH_REC = 102

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.listening_activity)
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

        val resLayer = findViewById<LinearLayout>(R.id.resLayer)
        val microLayer = findViewById<LinearLayout>(R.id.microLayer)
        val checkButton = findViewById<Button>(R.id.checkButton)

        checkButton.setOnClickListener {
            microLayer.visibility = View.VISIBLE
            resLayer.visibility = View.VISIBLE
            checkButton.visibility = View.GONE
        }

        val microCard = findViewById<CardView>(R.id.microCard)
        microCard.setOnClickListener {
            askSpeechInput()
        }

    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        val recText = findViewById<TextView>(R.id.recText)
        val microLayer = findViewById<LinearLayout>(R.id.microLayer)
        val checkButton = findViewById<Button>(R.id.checkButton)
        if (requestCode == RQ_SPEECH_REC && resultCode == Activity.RESULT_OK){
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            recText.text = result?.get(0).toString()

            if (recText.text.toString() != ""){
                if (recText.text.toString() == "cucumber"){
                    recText.setTextColor(Color.parseColor("#5BA890"))
                    microLayer.visibility = View.GONE
                    checkButton.visibility = View.VISIBLE
                    checkButton.text = getString(R.string.yay_go_next)
                    checkButton.setOnClickListener {
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }

                } else{
                    recText.setTextColor(Color.parseColor("#EF5DA8"))
                }
            }

        }
    }

    @RequiresApi(Build.VERSION_CODES.S)
    private fun askSpeechInput() {
        if (!SpeechRecognizer.isOnDeviceRecognitionAvailable(this)) {
            Toast.makeText(this, "Speech recognition is not available", Toast.LENGTH_LONG).show()
        } else {
            val i = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM)
            i.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
            i.putExtra(RecognizerIntent.EXTRA_PROMPT, "Say cucumber!")
            startActivityForResult(i, RQ_SPEECH_REC)
        }
    }
}