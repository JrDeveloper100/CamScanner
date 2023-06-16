package com.example.camscanner

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.io.File
import java.io.FileOutputStream


class Export : AppCompatActivity() {
    private lateinit var imageView29 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export)

        imageView29 = findViewById(R.id.imageView29)
        // Retrieve the file path from the extras
        // Retrieve the file path from the extras
        val filteredImagePath = intent.getStringExtra("filteredImagePath")

        // Load the image from the file

        // Load the image from the file
        val filteredImage = BitmapFactory.decodeFile(filteredImagePath)

        // Display the filtered image in the ImageView

        // Display the filtered image in the ImageView
        imageView29.setImageBitmap(filteredImage)
        imageView29.setOnClickListener {
            fullScreenPreview()
        }

    }

    private fun fullScreenPreview() {
        val drawable = imageView29.drawable as BitmapDrawable
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