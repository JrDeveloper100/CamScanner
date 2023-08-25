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

    private lateinit var imageView : ImageView
    private lateinit var retakeButton : Button
    private lateinit var btnNoCrop : LinearLayout
    private  var imageUri: Uri? = null
    private lateinit var brightnessSeekBar: SeekBar
    private lateinit var revertButton : ImageView
    private lateinit var improveColorsButton: ImageView
    private lateinit var sharpBlackButton: ImageView
    private lateinit var ocvBlackButton: ImageView
    private lateinit var btnEdit : LinearLayout
    private lateinit var rotateButton : LinearLayout
    private lateinit var toolbar: MaterialToolbar
    private lateinit var imageIndexTextView : TextView
//    private lateinit var photoEditorView: PhotoEditorView
    private lateinit var imageLayout : ConstraintLayout
    private var rotationAngle = 0f
    private lateinit var viewPager: ViewPager2
    private lateinit var photoEditorAdapter: photoEditorAdapter
    private val rotatedBitmaps: MutableList<Bitmap?> = mutableListOf()
    private val allAngles : MutableList<Float> = mutableListOf(90f,180f,270f)
    private var counter = 1;
    private var imageCurrentPosition = 0
    private var firstImageBrightness = 100
    private var secondImageBrightness = 100
//    private var cameraType : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

//        cameraType = intent.getStringExtra("cameraType")
        rotateButton = findViewById(R.id.rotateButton)
        retakeButton = findViewById(R.id.retakeButton)
        btnNoCrop = findViewById(R.id.btnNoCrop)
        brightnessSeekBar = findViewById(R.id.brightnessSeekBar)
        revertButton = findViewById(R.id.revertButton)
        improveColorsButton = findViewById(R.id.improveColorsButton)
        sharpBlackButton = findViewById(R.id.sharpBlackButton)
        ocvBlackButton = findViewById(R.id.ocvBlackButton)
        btnEdit = findViewById(R.id.btnEdit)
//        photoEditorView = findViewById(R.id.photoEditorView)
        imageLayout = findViewById(R.id.imageLayout)
        viewPager = findViewById(R.id.viewPager)
        imageIndexTextView = findViewById(R.id.imageIndexTextView)

//        if (cameraType == "Photo"){
//            val imageUri1 = intent.getStringExtra("imageUri1")?.toUri()
//            Constant.imageBasket.add(uriToBitmap(this,imageUri1))
//        }else{
//            val imageUri1 = intent.getStringExtra("imageUri1")?.toUri()
//            val imageUri2 = intent.getStringExtra("imageUri2")?.toUri()
//            Constant.imageBasket.add(uriToBitmap(this,imageUri1))
//            Constant.imageBasket.add(uriToBitmap(this,imageUri2))
//        }


        photoEditorAdapter = photoEditorAdapter(Constant.imageBasket, viewPager)
        viewPager.adapter = photoEditorAdapter
//        initOperations()


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
//                photoEditorView.source.colorFilter = setBrightness(progress / 2)
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
//            applyImprovementColors()
            photoEditorAdapter.applyImprovementColors(imageCurrentPosition)
            buttonSelectionLogic(2)
            updateBasket()
        }

        sharpBlackButton.setOnClickListener {
//            applySharpBlack()
            photoEditorAdapter.applySharpBlack(imageCurrentPosition)
            buttonSelectionLogic(3)
            updateBasket()
        }

        ocvBlackButton.setOnClickListener {
//            applyOcvBlack()
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

   private fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
       return try {
           MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
       } catch (e: IOException) {
           e.printStackTrace()
           null
       }
    }

    private fun revertToOriginal() {
//        photoEditorView.source.colorFilter = null
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
//        photoEditorView.source.colorFilter = filter
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
//        photoEditorView.source.colorFilter = filter
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
//        photoEditorView.source.colorFilter = filter
        buttonSelectionLogic(2)
    }

    private fun adjustBrightness(brightness: Float) {
        try {
            val currentImageView = viewPager.findViewWithTag<ImageView>("image_$imageCurrentPosition")
            if (currentImageView == null) {
                Toast.makeText(applicationContext,"null",Toast.LENGTH_SHORT).show()
                return
            }
            val colorMatrix = ColorMatrix().apply {
                set(floatArrayOf(
                    1f, 0f, 0f, 0f, brightness,
                    0f, 1f, 0f, 0f, brightness,
                    0f, 0f, 1f, 0f, brightness,
                    0f, 0f, 0f, 1f, 0f
                ))
            }

            val colorFilter = ColorMatrixColorFilter(colorMatrix)
            currentImageView?.colorFilter = colorFilter
        }catch (e: Exception){

        }
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

    private fun setBrightness(progress: Int): PorterDuffColorFilter {
        return if (progress >= 100) {
            PorterDuffColorFilter(
                Color.argb((progress - 100) * 255 / 100, 255, 255, 255),
                PorterDuff.Mode.SRC_OVER
            )
        } else PorterDuffColorFilter(
            Color.argb((100 - progress) * 255 / 100, 0, 0, 0),
            PorterDuff.Mode.SRC_ATOP
        )
    }

    private fun goToExportActivity() {
//        for (i in 0..1) {
//            println(":::::::::::::::::::::::::::::::::$i:::::::::::::::::::::::::::::::::::::::::::")
//            val bitmap = photoEditorAdapter.getMainFrameBitmap(i)
//            Constant.bitmapsToExport.add(bitmap)
//        }
//        Constant.original = getMainFrameBitmap()
//        val drawable = imageView.drawable as BitmapDrawable
//        val originalBitmap = drawable.bitmap
//        var outputStream: FileOutputStream
        try {
//            val outputFile = File(filesDir, "filtered_image.jpg")
//            outputStream = FileOutputStream(outputFile)
//            originalBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
//            outputStream.close()

            // Pass the file path as an extra in the Intent
            val intent = Intent(this, Export::class.java)
//            intent.putExtra("filteredImagePath", outputFile.absolutePath)
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
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        startActivity(intent)
//        finish()
    }

    private fun getMainFrameBitmap(): Bitmap? {
        val createBitmap = Bitmap.createBitmap(imageLayout.width, imageLayout.height, Bitmap.Config.ARGB_8888)
        imageLayout.draw(Canvas(createBitmap))
        return imageBitmap(createBitmap)
    }

    private fun imageBitmap(bitmap: Bitmap): Bitmap? {

        val width = bitmap.width
        var i = -1
        var height = bitmap.height
        var i2 = -1
        var i3 = width
        var i4 = 0
        while (i4 < bitmap.height) {
            var i5 = i2
            var i6 = i3
            for (i7 in 0 until bitmap.width) {
                if (bitmap.getPixel(i7, i4) shr 24 and 255 > 0) {
                    if (i7 < i6) {
                        i6 = i7
                    }
                    if (i7 > i) {
                        i = i7
                    }
                    if (i4 < height) {
                        height = i4
                    }
                    if (i4 > i5) {
                        i5 = i4
                    }
                }
            }
            i4++
            i3 = i6
            i2 = i5
        }
        return if (i < i3 || i2 < height) {
            null
        } else {
            Bitmap.createBitmap(bitmap, i3, height, i - i3 + 1, i2 - height + 1)
        }

    }

    private fun updateBasket(){
        Constant.imageBasket[imageCurrentPosition] = photoEditorAdapter.getMainFrameBitmap(imageCurrentPosition)
    }

    private fun showToast(msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }
}