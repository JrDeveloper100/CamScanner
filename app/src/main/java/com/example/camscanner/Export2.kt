package com.example.camscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Export2 : AppCompatActivity() {
    private lateinit var btnExportFile : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export2)

        btnExportFile = findViewById(R.id.btnExportFile)
        btnExportFile.setOnClickListener {
            val intent = Intent(this, AfterExport::class.java)
            startActivity(intent)
        }
    }
}