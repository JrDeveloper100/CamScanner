package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar

class Export2 : AppCompatActivity() {
    private lateinit var btnExportFile : Button
    private lateinit var toolbar : MaterialToolbar
    private lateinit var imageHost : ImageView
    private lateinit var imageHost2 : ImageView
    private lateinit var imageHost3 : ImageView
    private lateinit var imageHost4 : ImageView
    private lateinit var imageHost5 : ImageView
    private lateinit var imageHost6 : ImageView
    private lateinit var btnSingleImagePreview1 : ImageView
    private lateinit var btnSingleImagePreview2 : ImageView
    private lateinit var btnSingleImagePreview3 : ImageView
    private lateinit var btnSingleImagePreview4 : ImageView
    private lateinit var btnSingleImagePreview5 : ImageView
    private lateinit var btnSingleImagePreview6 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnExportFile = findViewById(R.id.btnExportFile)
        imageHost = findViewById(R.id.imageHost)
        imageHost2 = findViewById(R.id.imageHost2)
        imageHost3 = findViewById(R.id.imageHost3)
        imageHost4 = findViewById(R.id.imageHost4)
        imageHost5 = findViewById(R.id.imageHost5)
        imageHost6 = findViewById(R.id.imageHost6)
        btnSingleImagePreview1 = findViewById(R.id.btnSingleImagePreview1)
        btnSingleImagePreview2 = findViewById(R.id.btnSingleImagePreview2)
        btnSingleImagePreview3 = findViewById(R.id.btnSingleImagePreview3)
        btnSingleImagePreview4 = findViewById(R.id.btnSingleImagePreview4)
        btnSingleImagePreview5 = findViewById(R.id.btnSingleImagePreview5)
        btnSingleImagePreview6 = findViewById(R.id.btnSingleImagePreview6)
        btnExportFile.setOnClickListener {
            val intent = Intent(this, AfterExport::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview1.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview2.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview3.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview4.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview5.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        btnSingleImagePreview6.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        imageHost.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost2.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost3.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost4.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost5.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost6.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        if (isDarkModeEnabled(this)) {
            inflater.inflate(R.menu.top_app_bar_4_dark_mode, menu)
        } else {
            inflater.inflate(R.menu.top_app_bar_4_light_mode, menu)
        }
        return true
    }

    // Function to check if dark mode is enabled
    fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

}