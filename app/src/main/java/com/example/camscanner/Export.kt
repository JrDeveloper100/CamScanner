package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileOutputStream


class Export : AppCompatActivity() {
    private lateinit var imageHost : ImageView
    private lateinit var imageHost2 : ImageView
    private lateinit var imageHost3 : ImageView
    private lateinit var imageHost4 : ImageView
    private lateinit var imageHost5 : ImageView
    private lateinit var btnExport : Button
    private lateinit var toolbar : MaterialToolbar
    private lateinit var btnSingleImagePreview1 : ImageView
    private lateinit var btnSingleImagePreview2 : ImageView
    private lateinit var btnSingleImagePreview3 : ImageView
    private lateinit var btnSingleImagePreview4 : ImageView
    private lateinit var btnSingleImagePreview5 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnExport = findViewById(R.id.btnExport)
        imageHost = findViewById(R.id.imageHost)
        imageHost2 = findViewById(R.id.imageHost2)
        imageHost3 = findViewById(R.id.imageHost3)
        imageHost4 = findViewById(R.id.imageHost4)
        imageHost5 = findViewById(R.id.imageHost5)
        btnSingleImagePreview1 = findViewById(R.id.btnSingleImagePreview1)
        btnSingleImagePreview2 = findViewById(R.id.btnSingleImagePreview2)
        btnSingleImagePreview3 = findViewById(R.id.btnSingleImagePreview3)
        btnSingleImagePreview4 = findViewById(R.id.btnSingleImagePreview4)
        btnSingleImagePreview5 = findViewById(R.id.btnSingleImagePreview5)
        // Retrieve the file path from the extras
        // Retrieve the file path from the extras
//        val filteredImagePath = intent.getStringExtra("filteredImagePath")
//
//        // Load the image from the file
//
//        // Load the image from the file
//        val filteredImage = BitmapFactory.decodeFile(filteredImagePath)

        // Display the filtered image in the ImageView

        // Display the filtered image in the ImageView
        imageHost.setImageBitmap(Constant.original)
        imageHost.setOnClickListener {
            fullScreenPreview()
        }
        imageHost.setOnLongClickListener {
            // Handle long-press event here
            // Return true if the event is consumed and should not be propagated further
            // Return false if you want to allow the long-press event to be propagated further

            // Example: Show a toast message
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
            true // Event consumed
        }
        imageHost2.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost2.setOnLongClickListener {
            // Handle long-press event here
            // Return true if the event is consumed and should not be propagated further
            // Return false if you want to allow the long-press event to be propagated further

            // Example: Show a toast message
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
            true // Event consumed
        }
        imageHost3.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost3.setOnLongClickListener {
            // Handle long-press event here
            // Return true if the event is consumed and should not be propagated further
            // Return false if you want to allow the long-press event to be propagated further

            // Example: Show a toast message
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
            true // Event consumed
        }
        imageHost4.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost4.setOnLongClickListener {
            // Handle long-press event here
            // Return true if the event is consumed and should not be propagated further
            // Return false if you want to allow the long-press event to be propagated further

            // Example: Show a toast message
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
            true // Event consumed
        }
        imageHost5.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost5.setOnLongClickListener {
            // Handle long-press event here
            // Return true if the event is consumed and should not be propagated further
            // Return false if you want to allow the long-press event to be propagated further

            // Example: Show a toast message
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
            true // Event consumed
        }

        btnExport.setOnClickListener {

            goToActivity()

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

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }

    private fun goToActivity() {
//        val drawable = imageHost.drawable as BitmapDrawable
//        val originalBitmap = drawable.bitmap
//        var outputStream: FileOutputStream
        try {
//            val outputFile = File(filesDir, "filtered_image.jpg")
//            outputStream = FileOutputStream(outputFile)
//            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//            outputStream.close()

            // Pass the file path as an extra in the Intent
            val intent = Intent(this, Export2::class.java)
//            intent.putExtra("filteredImagePath", outputFile.absolutePath)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        if (isDarkModeEnabled(this)) {
            inflater.inflate(R.menu.top_app_bar_3_dark_mode, menu)
        } else {
            inflater.inflate(R.menu.top_app_bar_3_light_mode, menu)
        }
        return true
    }

    // Function to check if dark mode is enabled
    fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    private fun fullScreenPreview() {
        val drawable = imageHost.drawable as BitmapDrawable
        val originalBitmap = drawable.bitmap
        var outputStream: FileOutputStream
        try {
            val outputFile = File(filesDir, "filtered_image.jpg")
            outputStream = FileOutputStream(outputFile)
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            // Pass the file path as an extra in the Intent
            val intent = Intent(this, Preview::class.java)
            intent.putExtra("filteredImagePath", outputFile.absolutePath)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}