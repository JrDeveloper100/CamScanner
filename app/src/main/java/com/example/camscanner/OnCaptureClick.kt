package com.example.camscanner

import android.Manifest
import android.app.Dialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.hardware.camera2.CameraAccessException
import android.hardware.camera2.CameraCharacteristics
import android.hardware.camera2.CameraManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.util.Size
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.view.PreviewView
import androidx.camera.core.Preview
import androidx.camera.core.impl.CaptureConfig
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.net.toUri
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import com.google.android.material.appbar.MaterialToolbar
import java.io.IOException

class OnCaptureClick : AppCompatActivity() {


    private lateinit var cameraExecutor: ExecutorService
    private lateinit var imageCapture: ImageCapture
    private lateinit var imageView: ImageView
    private lateinit var captureButton: ImageView
    private lateinit var btnSwitchCamera: LinearLayout
    private lateinit var viewFinder : PreviewView
    private lateinit var selectImageButton : LinearLayout
    private lateinit var cameraManager: CameraManager
    private var cameraId: String? = null
    private val REQUEST_IMAGE_SELECTION = 1
    private val imageUri = "extra_image_uri"
    private var currentCameraLensFacing = CameraSelector.LENS_FACING_BACK
    private lateinit var toolbar: MaterialToolbar
    private lateinit var menu : Menu
    private lateinit var progressBar : ProgressBar
    private var isFlashOn = false
    private var flashAuto = false
    private lateinit var side : TextView
    private lateinit var squareLine : ImageView
    private lateinit var dottedLine: View

    private var currentPhotoIndex = 0
    private var imageUri1: Uri? = null
    private var imageUri2: Uri? = null
    private var cameraType : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_on_capture_click)

        PreviousActivityManager.setPreviousActivity(this::class.java)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        menu= toolbar.menu
        cameraType = intent.getStringExtra("cameraType")

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        cameraId = getCameraId()
        viewFinder = findViewById<PreviewView>(R.id.viewFinder)
        captureButton = findViewById(R.id.btnCapture)
        btnSwitchCamera = findViewById(R.id.btnSwitchCamera)
        selectImageButton = findViewById(R.id.selectImageButton)
        progressBar = findViewById(R.id.progressBar)
        side = findViewById(R.id.side)
        squareLine = findViewById(R.id.squareLine)
        dottedLine = findViewById(R.id.dottedLine)
        if (allPermissionsGranted()) {
            if (cameraType == "Photo"){
                squareLine.visibility = View.GONE
                startCamera()
            }else if (cameraType == "IDCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                iDCardDialog()
                startCamera()
            }else if (cameraType == "Document"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.GONE
                startCamera()
            }
            else if(cameraType == "Export"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.GONE
                startCamera()
            }else if (cameraType == "AcademicCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                academicCardDialog()
                startCamera()
            }else if (cameraType == "Book"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.VISIBLE
                startCamera()
            }else if (cameraType == "BusinessCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                businessCardDialog()
                startCamera()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
            if (cameraType == "Photo"){
                squareLine.visibility = View.GONE
                startCamera()
            }else if (cameraType == "IDCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                iDCardDialog()
                startCamera()
            }else if (cameraType == "Document"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.GONE
                startCamera()
            }
            else if(cameraType == "Export"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.GONE
                startCamera()
            }else if (cameraType == "AcademicCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                academicCardDialog()
                startCamera()
            }else if (cameraType == "Book"){
                squareLine.visibility = View.GONE
                dottedLine.visibility = View.VISIBLE
                startCamera()
            }else if (cameraType == "BusinessCard"){
                squareLine.visibility = View.VISIBLE
                dottedLine.visibility = View.GONE
                businessCardDialog()
                startCamera()
            }
        }

        captureButton.setOnClickListener {
            if (cameraType=="IDCard" && Constant.card_type == "Single"){
                takePhoto()
            }else if(cameraType=="IDCard" && Constant.card_type == "Double"){
                takeTwoPhotos()
            }else if (cameraType=="Book"){
                takeBookPhoto()
            }else if (cameraType=="AcademicCard" && Constant.card_type == "Single"){
                takePhoto()
            }else if (cameraType=="AcademicCard" && Constant.card_type == "Double"){
                takeTwoPhotos()
            }else if(cameraType=="BusinessCard" && Constant.card_type == "Single"){
                takePhoto()
            }else if(cameraType=="BusinessCard" && Constant.card_type == "Double"){
                takeTwoPhotos()
            }else{
                takePhoto()
            }

        }

        cameraExecutor = Executors.newSingleThreadExecutor()

        cameraManager = getSystemService(Context.CAMERA_SERVICE) as CameraManager
        try {
            cameraId = cameraManager.cameraIdList[0]
        } catch (e: CameraAccessException) {
            e.printStackTrace()
        }
        selectImageButton.setOnClickListener {
            openGallery()
        }
        btnSwitchCamera.setOnClickListener {
            switchCamera()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }

    override fun onResume() {
        super.onResume()
       if (!Constant.imageRetaking && !Constant.firstTime){
           if (cameraType == "Photo"){
               squareLine.visibility = View.GONE
               startCamera()
           }else if (cameraType == "IDCard"){
               squareLine.visibility = View.VISIBLE
               dottedLine.visibility = View.GONE
               iDCardDialog()
               startCamera()
           }else if (cameraType == "Document"){
               squareLine.visibility = View.GONE
               dottedLine.visibility = View.GONE
               startCamera()
           }
           else if(cameraType == "Export"){
               squareLine.visibility = View.GONE
               dottedLine.visibility = View.GONE
               startCamera()
           }else if (cameraType == "AcademicCard"){
               squareLine.visibility = View.VISIBLE
               dottedLine.visibility = View.GONE
               academicCardDialog()
               startCamera()
           }else if (cameraType == "Book"){
               squareLine.visibility = View.GONE
               dottedLine.visibility = View.VISIBLE
               startCamera()
           }else if (cameraType == "BusinessCard"){
               squareLine.visibility = View.VISIBLE
               dottedLine.visibility = View.GONE
               businessCardDialog()
               startCamera()
           }
       }
    }

    private fun takeTwoPhotos() {

        progressBar.visibility = View.VISIBLE
        squareLine.visibility = View.VISIBLE
        val imageCapture = imageCapture ?: return

        val photoFile = createImageFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        if (flashAuto){
            imageCapture.flashMode = ImageCapture.FLASH_MODE_AUTO
        }else{
            // Initialize imageCapture and add flash mode option
            imageCapture.flashMode = if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF

        }
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: photoFile.toUri()
                    if (currentPhotoIndex == 0) {
                        imageUri1 = savedUri
                        currentPhotoIndex = 1
                        progressBar.visibility = View.GONE
                        side.text = "Back Side"
                        // Capture the second photo
                    } else {
                        imageUri2 = savedUri
                        progressBar.visibility = View.GONE
                        side.visibility = View.GONE
                        Constant.original = null
                        val intent = Intent(this@OnCaptureClick, OnCaptureClick2::class.java)
//                        intent.putExtra("imageUri1", imageUri1.toString())
//                        intent.putExtra("imageUri2", imageUri2.toString())
//                        intent.putExtra("cameraType",cameraType)
                        if (Constant.imageRetaking){
                            Constant.imageBasket[Constant.originalImagePosition] = uriToBitmap(this@OnCaptureClick, savedUri)
                        }else{
                            Constant.imageBasket.add(uriToBitmap(this@OnCaptureClick,imageUri1))
                            Constant.imageBasket.add(uriToBitmap(this@OnCaptureClick,imageUri2))
                        }
                        startActivity(intent)
                    }
                }

                override fun onError(exception: ImageCaptureException) {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            }
        )

    }

    private fun takeBookPhoto(){
        progressBar.visibility = View.VISIBLE
        val imageCapture = imageCapture ?: return

        val photoFile = createImageFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        if (flashAuto) {
            imageCapture.flashMode = ImageCapture.FLASH_MODE_AUTO
        } else {
            imageCapture.flashMode =
                if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF
        }

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: photoFile.toUri()

                    val capturedBitmap = uriToBitmap(this@OnCaptureClick, savedUri)
                    val height = capturedBitmap?.height ?: 0
                    val width = capturedBitmap?.width ?: 0
                    val middleY = height / 2
                    val upperBitmap = capturedBitmap?.let { Bitmap.createBitmap(it, 0, 0, width, middleY) }
                    val lowerBitmap = capturedBitmap?.let { Bitmap.createBitmap(it, 0, middleY, width, height-middleY) }
                    Constant.imageBasket.clear()
                    // Display the upper and lower images in the respective ImageViews
                    Constant.imageBasket.add(upperBitmap)
                    Constant.imageBasket.add(lowerBitmap)
                    val intent = Intent(this@OnCaptureClick, OnCaptureClick2::class.java)
                    startActivity(intent)
                    progressBar.visibility = View.GONE
                    Constant.original = null
                }

                override fun onError(exception: ImageCaptureException) {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            }
        )
    }

    private fun iDCardDialog() {

        val dialog = Dialog(this, R.style.ThemeWithRoundShape)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.id_card_selection_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tv_select1)?.setOnClickListener {
            Constant.card_type = "Single"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.tv_select2)?.setOnClickListener {
            side.visibility = View.VISIBLE
            Constant.card_type = "Double"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<ImageView>(R.id.iv_close)?.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()

    }

    private fun academicCardDialog() {

        val dialog = Dialog(this, R.style.ThemeWithRoundShape)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.academic_card_selection_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tv_select1)?.setOnClickListener {
            Constant.card_type = "Single"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.tv_select2)?.setOnClickListener {
            side.visibility = View.VISIBLE
            Constant.card_type = "Double"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<ImageView>(R.id.iv_close)?.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()

    }

    private fun businessCardDialog() {

        val dialog = Dialog(this, R.style.ThemeWithRoundShape)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.business_card_selection_dialog)
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCanceledOnTouchOutside(false)
        dialog.setCancelable(false)

        dialog.findViewById<TextView>(R.id.tv_select1)?.setOnClickListener {
            Constant.card_type = "Single"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<TextView>(R.id.tv_select2)?.setOnClickListener {
            side.visibility = View.VISIBLE
            Constant.card_type = "Double"
            currentPhotoIndex = 0
            dialog.dismiss()
        }

        dialog.findViewById<ImageView>(R.id.iv_close)?.setOnClickListener {
            dialog.dismiss()
            finish()
        }

        dialog.show()

    }

    private fun getCameraId(): String? {
        val cameraIds = cameraManager.cameraIdList
        for (id in cameraIds) {
            val characteristics = cameraManager.getCameraCharacteristics(id)
            val flashAvailable = characteristics.get(CameraCharacteristics.FLASH_INFO_AVAILABLE)
            val lensFacing = characteristics.get(CameraCharacteristics.LENS_FACING)
            if (flashAvailable == true && lensFacing == CameraCharacteristics.LENS_FACING_BACK) {
                return id
            }
        }
        return null
    }

    private fun switchCamera() {
        currentCameraLensFacing = when (currentCameraLensFacing) {
            CameraSelector.LENS_FACING_BACK -> CameraSelector.LENS_FACING_FRONT
            CameraSelector.LENS_FACING_FRONT -> CameraSelector.LENS_FACING_BACK
            else -> currentCameraLensFacing
        }

        startCamera()
    }

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_IMAGE_SELECTION)
    }


    private fun turnFlashOff() {
        cameraId?.let { id ->
            try {
                cameraManager.setTorchMode(id, false)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun turnFlashOn() {
        cameraId?.let { id ->
            try {
                cameraManager.setTorchMode(id, true)
            } catch (e: CameraAccessException) {
                e.printStackTrace()
            }
        }
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(viewFinder.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.Builder()
                .requireLensFacing(currentCameraLensFacing)
                .build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (e: Exception) {
                Log.e(TAG, "Failed to bind camera: $e")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        progressBar.visibility = View.VISIBLE
        val imageCapture = imageCapture ?: return

        val photoFile = createImageFile()

        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        if (flashAuto){
            imageCapture.flashMode = ImageCapture.FLASH_MODE_AUTO
        }else{
            // Initialize imageCapture and add flash mode option
            imageCapture.flashMode = if (isFlashOn) ImageCapture.FLASH_MODE_ON else ImageCapture.FLASH_MODE_OFF

        }
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    val savedUri = outputFileResults.savedUri ?: photoFile.toUri()
                    val intent = Intent(this@OnCaptureClick, OnCaptureClick2::class.java)
//                    intent.putExtra("imageUri1", savedUri.toString())
//                    intent.putExtra("cameraType",cameraType)
                    if (Constant.imageRetaking){
                        Constant.imageBasket[Constant.originalImagePosition] = uriToBitmap(this@OnCaptureClick, savedUri)
                    }else{
                        Constant.imageBasket.add(uriToBitmap(this@OnCaptureClick, savedUri))
                    }

                    progressBar.visibility = View.GONE
                    Constant.original = null
                    startActivity(intent)
                }

                override fun onError(exception: ImageCaptureException) {
                    progressBar.visibility = View.GONE
                    Log.e(TAG, "Photo capture failed: ${exception.message}", exception)
                }
            }

        )
    }

    private fun uriToBitmap(context: Context, uri: Uri?): Bitmap? {
        return try {
            MediaStore.Images.Media.getBitmap(context.contentResolver, uri)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun createImageFile(): File {
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
        val storageDir: File = getExternalFilesDir(null)!!
        return File.createTempFile(
            "JPEG_${timeStamp}_",
            ".jpg",
            storageDir
        )
    }

    private fun allPermissionsGranted(): Boolean {
        for (permission in REQUIRED_PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImageUri: Uri? = data?.data
        if (selectedImageUri != null) {
            val contentResolver = contentResolver
            val contentValues = ContentValues().apply {
                put(MediaStore.Images.Media.DISPLAY_NAME, "Image.jpg")
                put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
            }
            val imageUri = contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues)
            imageUri?.let {
                contentResolver.openOutputStream(it)?.use { outputStream ->
                    contentResolver.openInputStream(selectedImageUri)?.use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }
                val intent = Intent(this, OnCaptureClick2::class.java)
                intent.putExtra("imageUri", imageUri.toString())
                startActivity(intent)
            } ?: showToast("Failed to create image file")
        } else {
            showToast("No Image Selected")
        }
    }

    private fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        if (isDarkModeEnabled(this)) {
            inflater.inflate(R.menu.top_app_bar_dark_mode, menu)
        } else {
            inflater.inflate(R.menu.top_app_bar_light_mode, menu)
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Go back to the previous activity
                true
            }
            R.id.menu_flash_auto -> {
                toggleFlashIcon(1)
                true
            }
            R.id.menu_flash_on -> {
                toggleFlashIcon(2)
                true
            }
            R.id.menu_flash_off -> {
                toggleFlashIcon(3)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun toggleFlashIcon(value : Int) {
        if(value==1){
            menu.findItem(R.id.menu_flash_on).isVisible = true
            menu.findItem(R.id.menu_flash_off).isVisible = false
            menu.findItem(R.id.menu_flash_auto).isVisible = false
            isFlashOn = true
        }else if (value==2){
            menu.findItem(R.id.menu_flash_on).isVisible = false
            menu.findItem(R.id.menu_flash_off).isVisible = true
            menu.findItem(R.id.menu_flash_auto).isVisible = false
            isFlashOn = false
        }else{
            menu.findItem(R.id.menu_flash_on).isVisible = false
            menu.findItem(R.id.menu_flash_off).isVisible = false
            menu.findItem(R.id.menu_flash_auto).isVisible = true
            flashAuto = true
        }
    }

    private fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

}