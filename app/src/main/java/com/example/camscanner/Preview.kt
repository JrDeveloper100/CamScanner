package com.example.camscanner

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import com.google.android.material.appbar.MaterialToolbar

class Preview : AppCompatActivity() {
    private lateinit var fullScreenImageView : ImageView
    private lateinit var btnClosePreview : Button
    private lateinit var toolbar: MaterialToolbar
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        fullScreenImageView = findViewById(R.id.fullScreenImageView)
        btnClosePreview = findViewById(R.id.btnClosePreview)
        // Retrieve the file path from the extras
        // Retrieve the file path from the extras
        val filteredImagePath = intent.getStringExtra("filteredImagePath")

        // Load the image from the file

        // Load the image from the file
        val filteredImage = BitmapFactory.decodeFile(filteredImagePath)

        // Display the filtered image in the ImageView

        // Display the filtered image in the ImageView
        fullScreenImageView.setImageBitmap(filteredImage)
        btnClosePreview.setOnClickListener {
            finish()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }
}