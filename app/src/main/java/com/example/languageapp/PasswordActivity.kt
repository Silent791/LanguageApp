package com.example.languageapp

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.TextPaint
import android.text.method.LinkMovementMethod
import android.text.style.BackgroundColorSpan
import android.text.style.ClickableSpan
import android.text.style.ForegroundColorSpan
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PasswordActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.password_activity)

        val textView = findViewById<TextView>(R.id.login)
        val text = "Already you member? Login"
        val textString = SpannableString(text)
        val wordText = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@PasswordActivity, AuthActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {

            }
        }

        val currentNightMode = AppCompatDelegate.getDefaultNightMode()
        val backgroundColor = when (currentNightMode) {
            AppCompatDelegate.MODE_NIGHT_NO -> Color.WHITE
            AppCompatDelegate.MODE_NIGHT_YES -> Color.parseColor("#080E1E")
            else -> Color.WHITE
        }
        textString.setSpan(BackgroundColorSpan(backgroundColor), 20, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(wordText, 20, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textString.setSpan(ForegroundColorSpan(Color.parseColor("#5B7BFE")), 20, 25, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = textString
        textView.movementMethod = LinkMovementMethod.getInstance()

        val backButton = findViewById<ImageButton>(R.id.page_back)
        backButton.setOnClickListener {
            finish()
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

        val signupButton = findViewById<Button>(R.id.signup)
        signupButton.setOnClickListener {
            val intent = Intent(this@PasswordActivity, OnboardingActivity1::class.java)
            startActivity(intent)
        }
    }
}