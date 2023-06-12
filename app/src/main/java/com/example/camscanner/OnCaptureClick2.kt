package com.example.camscanner

import android.graphics.Bitmap
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView

class OnCaptureClick2 : AppCompatActivity() {

    private val rotationAngles = listOf(0f, 90f, 180f, 270f)
    private var currentRotationIndex = 0
    private lateinit var imageView : ImageView
    private lateinit var retakeButton : Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click2)

        val rotateButton = findViewById<ImageView>(R.id.rotateButton)
        retakeButton = findViewById(R.id.retakeButton)
        val imageUriString = intent.getStringExtra("imageUri")
        val imageUri = Uri.parse(imageUriString)
        imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(imageUri)
        rotateButton.setOnClickListener {
            rotateImage(imageUri)
        }
        retakeButton.setOnClickListener {
            retakePicture()
        }
    }

    private fun retakePicture() {
        finish()
    }

    private fun rotateImage(imageUri: Uri?) {
        val bitmap = MediaStore.Images.Media.getBitmap(contentResolver, imageUri)
        val currentAngle = rotationAngles[currentRotationIndex]
        val matrix = Matrix()
        matrix.postRotate(currentAngle)
        val rotatedBitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
        val imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageBitmap(rotatedBitmap)
        currentRotationIndex = (currentRotationIndex + 1) % rotationAngles.size
    }
}