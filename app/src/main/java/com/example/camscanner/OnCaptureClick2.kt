package com.example.camscanner
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar

class OnCaptureClick2 : AppCompatActivity() {

    private val rotationAngles = listOf(0f, 90f, 180f, 270f)
    private var currentRotationIndex = 0
    private lateinit var imageView : ImageView
    private lateinit var retakeButton : Button
    private lateinit var btnNoCrop : ImageView
    private lateinit var imageUri: Uri
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var revertButton : ImageView
    private lateinit var improveColorsButton: ImageView
    private lateinit var sharpBlackButton: ImageView
    private lateinit var ocvBlackButton: ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click2)

        val rotateButton = findViewById<ImageView>(R.id.rotateButton)
        retakeButton = findViewById(R.id.retakeButton)
        btnNoCrop = findViewById(R.id.btnNoCrop)
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar)
        revertButton = findViewById(R.id.revertButton)
        improveColorsButton = findViewById(R.id.improveColorsButton)
        sharpBlackButton = findViewById(R.id.sharpBlackButton)
        ocvBlackButton = findViewById(R.id.ocvBlackButton)

        val imageUriString = intent.getStringExtra("imageUri")
        imageUri = Uri.parse(imageUriString)
        imageView = findViewById<ImageView>(R.id.imageView)
        imageView.setImageURI(imageUri)

        rotateButton.setOnClickListener {
            rotateImage(imageUri)
        }

        retakeButton.setOnClickListener {
            retakePicture()
        }

        btnNoCrop.setOnClickListener {

        }

        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val brightness = progress - 100
                adjustBrightness(brightness)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {}
        })

        revertButton.setOnClickListener {
            revertToOriginal()
        }

        improveColorsButton.setOnClickListener {
            applyImprovementColors()
        }

        sharpBlackButton.setOnClickListener {
            applySharpBlack()
        }

        ocvBlackButton.setOnClickListener {
            applyOcvBlack()
        }

    }

    private fun revertToOriginal() {
        imageView.colorFilter = null
        brightnessSeekBar.progress = 100
    }

    private fun applyOcvBlack() {
        val matrix = ColorMatrix().apply {
            setSaturation(1f) // Reset the saturation
            postConcat(ColorMatrix(floatArrayOf(
                0.2f, 0f, 0f, 0f, 0f,
                0f, 0.2f, 0f, 0f, 0f,
                0f, 0f, 0.2f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )))
        }

        val filter = ColorMatrixColorFilter(matrix)
        imageView.colorFilter = filter
        ocvBlackButton.setImageResource(R.drawable.ocv_black_color_selected)
    }

    private fun applySharpBlack() {
        val matrix = ColorMatrix().apply {
            setSaturation(1f) // Reset the saturation
            postConcat(ColorMatrix(floatArrayOf(
                1.3f, 0f, 0f, 0f, -150f,
                0f, 1.3f, 0f, 0f, -150f,
                0f, 0f, 1.3f, 0f, -150f,
                0f, 0f, 0f, 1f, 0f
            )))
        }

        val filter = ColorMatrixColorFilter(matrix)
        imageView.colorFilter = filter
        sharpBlackButton.setImageResource(R.drawable.sharp_black_color_selected)
    }

    private fun applyImprovementColors() {
        val matrix = ColorMatrix().apply {
            setSaturation(1f) // Reset the saturation
            postConcat(ColorMatrix(floatArrayOf(
                1.3f, 0f, 0f, 0f, 0f,
                0f, 1.3f, 0f, 0f, 0f,
                0f, 0f, 1.3f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f
            )))
        }

        val filter = ColorMatrixColorFilter(matrix)
        imageView.colorFilter = filter
        improveColorsButton.setImageResource(R.drawable.improve_colors_selected)
    }

    private fun adjustBrightness(brightness: Int) {
        val matrix = ColorMatrix().apply {
            set(floatArrayOf(
                1f, 0f, 0f, 0f, brightness.toFloat(),
                0f, 1f, 0f, 0f, brightness.toFloat(),
                0f, 0f, 1f, 0f, brightness.toFloat(),
                0f, 0f, 0f, 1f, 0f
            ))
        }

        val filter = ColorMatrixColorFilter(matrix)

        imageView.colorFilter = filter
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