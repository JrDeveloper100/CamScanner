package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
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
    private lateinit var exportAdapter : ExportAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        btnExport = findViewById(R.id.btnExport)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // Set spanCount based on your preference
        exportAdapter = ExportAdapter(Constant.imageBasket,this)
        recyclerView.adapter = exportAdapter

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

    fun deleteItem(position: Int){
        if (position==0){
            Constant.imageBasket.removeAt(position)
            exportAdapter.notifyItemRemoved(1)
            exportAdapter.notifyItemRangeChanged(position,Constant.imageBasket.size)
        }else{
            Constant.imageBasket.removeAt(position)
            exportAdapter.notifyItemRemoved(position)
            exportAdapter.notifyItemRangeChanged(position,Constant.imageBasket.size)
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Go back to the previous activity
                true
            }
            R.id.search -> {

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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

    fun goToCameraActivity(){
        try {
            val intent = Intent(this, OnCaptureClick::class.java)
            intent.putExtra("cameraType","Export")
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}