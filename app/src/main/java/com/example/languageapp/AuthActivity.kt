package com.example.languageapp

import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.appcompat.app.AppCompatDelegate


class AuthActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.auth_activity)

        val textView = findViewById<TextView>(R.id.textGoogle)
        val text = "Use can use Google for sign in"
        val googleString = SpannableString(text)
        val googleText = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val url = "https://google.com"
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
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
        googleString.setSpan(BackgroundColorSpan(backgroundColor), 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        googleString.setSpan(googleText, 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        googleString.setSpan(ForegroundColorSpan(Color.parseColor("#5B7BFE")), 12, 18, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView.text = googleString
        textView.movementMethod = LinkMovementMethod.getInstance()

        val textView1 = findViewById<TextView>(R.id.signup)
        val text1 = "Not you member? Signup"
        val spanString = SpannableString(text1)
        val signupText = object : ClickableSpan() {
            override fun onClick(widget: View) {
                val intent = Intent(this@AuthActivity, SignUpActivity::class.java)
                startActivity(intent)
            }
            override fun updateDrawState(ds: TextPaint) {
            }
        }
        spanString.setSpan(BackgroundColorSpan(backgroundColor), 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanString.setSpan(signupText, 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        spanString.setSpan(ForegroundColorSpan(Color.parseColor("#5B7BFE")), 16, 22, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        textView1.text = spanString
        textView1.movementMethod = LinkMovementMethod.getInstance()

        val backButton = findViewById<ImageButton>(R.id.page_back)
        backButton.setOnClickListener {
            finish()
        }

        val loginButton = findViewById<Button>(R.id.loginButton)
        loginButton.setOnClickListener {
            val intent = Intent(this@AuthActivity, OnboardingActivity1::class.java)
            startActivity(intent)
        }

        window.statusBarColor = Color.parseColor("#410FA3")
        window.navigationBarColor = Color.parseColor("#410FA3")

        val networkManager = NetworkManager(this)
        networkManager.observe(this) { hasNetwork ->
            if (!hasNetwork) {
                val intent = Intent(this, NetworkErrorActivity::class.java)
                startActivity(intent)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

}