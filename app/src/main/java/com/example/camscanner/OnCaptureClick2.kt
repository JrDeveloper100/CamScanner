package com.example.camscanner
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.appbar.MaterialToolbar
import java.io.File
import java.io.FileOutputStream


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
    private lateinit var btnEdit : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click2)

        val toolbar: MaterialToolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val rotateButton = findViewById<ImageView>(R.id.rotateButton)
        retakeButton = findViewById(R.id.retakeButton)
        btnNoCrop = findViewById(R.id.btnNoCrop)
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar)
        revertButton = findViewById(R.id.revertButton)
        improveColorsButton = findViewById(R.id.improveColorsButton)
        sharpBlackButton = findViewById(R.id.sharpBlackButton)
        ocvBlackButton = findViewById(R.id.ocvBlackButton)
        btnEdit = findViewById(R.id.btnEdit)
        btnEdit.setOnClickListener {
            val intent = Intent(this, EditDocument::class.java)
            startActivity(intent)
        }

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
        buttonSelectionLogic(1)
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
        buttonSelectionLogic(4)
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
        buttonSelectionLogic(3)
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
        buttonSelectionLogic(2)
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

    private fun buttonSelectionLogic(button: Int){
        when (button) {
            1 -> {
                revertButton.setImageResource(R.drawable.original_selected)
                improveColorsButton.setImageResource(R.drawable.rectangle_2)
                sharpBlackButton.setImageResource(R.drawable.rectangle_3)
                ocvBlackButton.setImageResource(R.drawable.rectangle_4)
            }
            2 -> {
                revertButton.setImageResource(R.drawable.rectangle_1)
                improveColorsButton.setImageResource(R.drawable.improve_colors_selected)
                sharpBlackButton.setImageResource(R.drawable.rectangle_3)
                ocvBlackButton.setImageResource(R.drawable.rectangle_4)
            }
            3 -> {
                revertButton.setImageResource(R.drawable.rectangle_1)
                improveColorsButton.setImageResource(R.drawable.rectangle_2)
                sharpBlackButton.setImageResource(R.drawable.sharp_black_color_selected)
                ocvBlackButton.setImageResource(R.drawable.rectangle_4)
            }
            else -> {
                revertButton.setImageResource(R.drawable.rectangle_1)
                improveColorsButton.setImageResource(R.drawable.rectangle_2)
                sharpBlackButton.setImageResource(R.drawable.rectangle_3)
                ocvBlackButton.setImageResource(R.drawable.ocv_black_color_selected)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.top_app_bar_2, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Go back to the previous activity
                true
            }
            R.id.done -> {
                goToActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToActivity() {
        val drawable = imageView.drawable as BitmapDrawable
        val originalBitmap = drawable.bitmap
        var outputStream: FileOutputStream
        try {
            val outputFile = File(filesDir, "filtered_image.jpg")
            outputStream = FileOutputStream(outputFile)
            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.close()

            // Pass the file path as an extra in the Intent
            val intent = Intent(this, Export::class.java)
            intent.putExtra("filteredImagePath", outputFile.absolutePath)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun showToast(msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}