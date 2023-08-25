package com.example.camscanner

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnGetStarted : Button = findViewById(R.id.btnGetStarted)
        btnGetStarted.setOnClickListener {
            val sharedPreferences = getSharedPreferences("MyAppPrefs", Context.MODE_PRIVATE)
            sharedPreferences.edit().putBoolean("isGetStartedShown", true).apply()
            val intent = Intent(this, Home::class.java)
            startActivity(intent)
            finish()
        }
    }
}