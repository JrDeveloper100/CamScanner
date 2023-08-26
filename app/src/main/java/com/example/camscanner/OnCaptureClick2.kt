package com.example.camscanner
import android.app.UiModeManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import android.graphics.Matrix
import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.drawToBitmap
import androidx.core.view.get
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.appbar.MaterialToolbar
import ja.burhanrashid52.photoeditor.PhotoEditorView
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class OnCaptureClick2 : AppCompatActivity() {

    private lateinit var retakeButton : Button
    private lateinit var btnNoCrop : LinearLayout
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var revertButton : ImageView
    private lateinit var improveColorsButton: ImageView
    private lateinit var sharpBlackButton: ImageView
    private lateinit var ocvBlackButton: ImageView
    private lateinit var btnEdit : LinearLayout
    private lateinit var rotateButton : LinearLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var imageIndexTextView : TextView
    private lateinit var imageLayout : ConstraintLayout
    private lateinit var viewPager: ViewPager2
    private lateinit var photoEditorAdapter: photoEditorAdapter
    private val allAngles : MutableList<Float> = mutableListOf(90f,180f,270f)
    private var counter = 1;
    private var imageCurrentPosition = 0
    private var firstImageBrightness = 100
    private var secondImageBrightness = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        rotateButton = findViewById(R.id.rotateButton)
        retakeButton = findViewById(R.id.retakeButton)
        btnNoCrop = findViewById(R.id.btnNoCrop)
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar)
        revertButton = findViewById(R.id.revertButton)
        improveColorsButton = findViewById(R.id.improveColorsButton)
        sharpBlackButton = findViewById(R.id.sharpBlackButton)
        ocvBlackButton = findViewById(R.id.ocvBlackButton)
        btnEdit = findViewById(R.id.btnEdit)
        imageLayout = findViewById(R.id.imageLayout)
        viewPager = findViewById(R.id.viewPager)
        imageIndexTextView = findViewById(R.id.imageIndexTextView)

        photoEditorAdapter = photoEditorAdapter(Constant.imageBasket, viewPager)
        viewPager.adapter = photoEditorAdapter


        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                imageCurrentPosition = position
                if (Constant.selectedFilterPosition == imageCurrentPosition){
                    photoEditorAdapter.keepTrackFilterApplied(imageCurrentPosition)
                }
                val imageIndex = position + 1
                val totalImages = Constant.imageBasket.size
                imageIndexTextView.text = "$imageIndex/$totalImages"
                if (position==0){
                    brightnessSeekBar.progress = firstImageBrightness
                }else{
                    brightnessSeekBar.progress = secondImageBrightness
                }

            }
        })
        btnEdit.setOnClickListener {
            goToEditDocumentActivity()
        }

        rotateButton.setOnClickListener {

//            rotationAngle = (rotationAngle + 90) % 360
//            photoEditorView.rotation = rotationAngle.toFloat()
            rotateImage()
            updateBasket()
        }

        retakeButton.setOnClickListener {
            retakePicture()
        }

        btnNoCrop.setOnClickListener {
            goToCropActivity()
        }

        brightnessSeekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                val brightness = (progress - 100).toFloat()
                if (imageCurrentPosition==0){
                    firstImageBrightness = progress
                }else{
                    secondImageBrightness = progress
                }
                photoEditorAdapter.setBrightness(imageCurrentPosition, brightness)
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {}

            override fun onStopTrackingTouch(seekBar: SeekBar) {
                updateBasket()
            }
        })

        revertButton.setOnClickListener {
//            revertToOriginal()
            photoEditorAdapter.revertToOriginal(imageCurrentPosition)
            buttonSelectionLogic(1)
            updateBasket()
        }

        improveColorsButton.setOnClickListener {
            photoEditorAdapter.applyImprovementColors(imageCurrentPosition)
            buttonSelectionLogic(2)
            updateBasket()
        }

        sharpBlackButton.setOnClickListener {
            photoEditorAdapter.applySharpBlack(imageCurrentPosition)
            buttonSelectionLogic(3)
            updateBasket()
        }

        ocvBlackButton.setOnClickListener {
            photoEditorAdapter.applyOcvBlack(imageCurrentPosition)
            buttonSelectionLogic(4)
            updateBasket()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
            rotateButton.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnEdit.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnNoCrop.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
            rotateButton.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnEdit.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnNoCrop.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
        }

    }

    override fun onBackPressed() {
        super.onBackPressed()
        Constant.imageBasket.clear()
        Constant.imagesPath.clear()
        Constant.imageRetaking = false
        Constant.firstTime = false
    }

    override fun onResume() {
        super.onResume()
        val previousActivity = PreviousActivityManager.getPreviousActivity()

        if (previousActivity == OnCaptureClick::class.java && Constant.original==null){
//            val imageUriString = intent.getStringExtra("imageUri")
//            val imageUriString = intent.getStringExtra("imageUri1")
//            imageUri = Uri.parse(imageUriString)
//            photoEditorView.source.setImageURI(imageUri)
        }else{
            Constant.imageBasket[Constant.originalImagePosition] = Constant.original
            photoEditorAdapter.notifyDataSetChanged()
        }
    }

    private fun rotateImage() {
        val currentPosition = viewPager.currentItem
        var newRotationAngle = allAngles[counter]
        photoEditorAdapter.setRotationAngle(currentPosition, newRotationAngle)
    }


    private fun retakePicture() {
        Constant.originalImagePosition = imageCurrentPosition
        Constant.imageRetaking = true
        Constant.card_type = "Single"
        finish()
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
        val inflater = menuInflater
        if (isDarkModeEnabled(this)) {
            inflater.inflate(R.menu.top_app_bar_2_dark_mode, menu)
        } else {
            inflater.inflate(R.menu.top_app_bar_2_light_mode, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Go back to the previous activity
                true
            }
            R.id.done -> {
                goToExportActivity()
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

    private fun goToExportActivity() {
        try {
            // Pass the file path as an extra in the Intent
            val intent = Intent(this, Export::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun goToEditDocumentActivity() {
        Constant.original = photoEditorAdapter.getMainFrameBitmap(imageCurrentPosition)
        Constant.originalImagePosition = imageCurrentPosition
        val intent = Intent(this, EditDocument::class.java)
        startActivity(intent)
    }

    private fun goToCropActivity(){
        Constant.original = photoEditorAdapter.getMainFrameBitmap(imageCurrentPosition)
        Constant.originalImagePosition = imageCurrentPosition
        val intent = Intent(this, CropActivity::class.java)
        startActivity(intent)
    }

    private fun updateBasket(){
        Constant.imageBasket[imageCurrentPosition] = photoEditorAdapter.getMainFrameBitmap(imageCurrentPosition)
    }
}