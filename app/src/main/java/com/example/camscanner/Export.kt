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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileOutputStream


class Export : AppCompatActivity() {
    private lateinit var btnExport : Button
    private lateinit var toolbar : MaterialToolbar
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        btnExport = findViewById(R.id.btnExport)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // Set spanCount based on your preference
        val exportAdapter = ExportAdapter(Constant.imageBasket,this)
        recyclerView.adapter = exportAdapter
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

//        imageHost.setOnLongClickListener {
//            // Handle long-press event here
//            // Return true if the event is consumed and should not be propagated further
//            // Return false if you want to allow the long-press event to be propagated further
//
//            // Example: Show a toast message
//            val intent = Intent(this,RemoveImages::class.java)
//            startActivity(intent)
//            true // Event consumed
//        }
//        imageHost2.setOnClickListener {
//            val intent = Intent(this,Preview::class.java)
//            startActivity(intent)
//        }
//        imageHost2.setOnLongClickListener {
//            // Handle long-press event here
//            // Return true if the event is consumed and should not be propagated further
//            // Return false if you want to allow the long-press event to be propagated further
//
//            // Example: Show a toast message
//            val intent = Intent(this,RemoveImages::class.java)
//            startActivity(intent)
//            true // Event consumed
//        }
//        imageHost3.setOnClickListener {
//            val intent = Intent(this,Preview::class.java)
//            startActivity(intent)
//        }
//        imageHost3.setOnLongClickListener {
//            // Handle long-press event here
//            // Return true if the event is consumed and should not be propagated further
//            // Return false if you want to allow the long-press event to be propagated further
//
//            // Example: Show a toast message
//            val intent = Intent(this,RemoveImages::class.java)
//            startActivity(intent)
//            true // Event consumed
//        }
//        imageHost4.setOnClickListener {
//            val intent = Intent(this,Preview::class.java)
//            startActivity(intent)
//        }
//        imageHost4.setOnLongClickListener {
//            // Handle long-press event here
//            // Return true if the event is consumed and should not be propagated further
//            // Return false if you want to allow the long-press event to be propagated further
//
//            // Example: Show a toast message
//            val intent = Intent(this,RemoveImages::class.java)
//            startActivity(intent)
//            true // Event consumed
//        }
//        imageHost5.setOnClickListener {
//            val intent = Intent(this,Preview::class.java)
//            startActivity(intent)
//        }
//        imageHost5.setOnLongClickListener {
//            // Handle long-press event here
//            // Return true if the event is consumed and should not be propagated further
//            // Return false if you want to allow the long-press event to be propagated further
//
//            // Example: Show a toast message
//            val intent = Intent(this,RemoveImages::class.java)
//            startActivity(intent)
//            true // Event consumed
//        }

        btnExport.setOnClickListener {
            goToActivity()
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

    fun singleImagePreview(position : Int){
        val intent = Intent(this,SingleImagePreview::class.java)
        intent.putExtra("position",position)
        startActivity(intent)
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
    private fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    fun fullScreenPreview() {
        try {
            val intent = Intent(this, Preview::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}