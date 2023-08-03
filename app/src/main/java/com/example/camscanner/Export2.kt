package com.example.camscanner

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Rect
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.Menu
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.core.net.toUri
import com.google.android.material.appbar.MaterialToolbar
import com.itextpdf.text.Document
import com.itextpdf.text.Paragraph
import com.itextpdf.text.pdf.PdfWriter
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.OutputStream

class Export2 : AppCompatActivity() {
    private lateinit var btnExportFile : Button
    private lateinit var toolbar : MaterialToolbar
    private lateinit var imageHost : ImageView
    private lateinit var imageHost2 : ImageView
    private lateinit var imageHost3 : ImageView
    private lateinit var imageHost4 : ImageView
    private lateinit var imageHost5 : ImageView
    private lateinit var imageHost6 : ImageView
    private lateinit var btnSingleImagePreview1 : ImageView
    private lateinit var btnSingleImagePreview2 : ImageView
    private lateinit var btnSingleImagePreview3 : ImageView
    private lateinit var btnSingleImagePreview4 : ImageView
    private lateinit var btnSingleImagePreview5 : ImageView
    private lateinit var btnSingleImagePreview6 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnExportFile = findViewById(R.id.btnExportFile)
        imageHost = findViewById(R.id.imageHost)
        imageHost2 = findViewById(R.id.imageHost2)
        imageHost3 = findViewById(R.id.imageHost3)
        imageHost4 = findViewById(R.id.imageHost4)
        imageHost5 = findViewById(R.id.imageHost5)
        imageHost6 = findViewById(R.id.imageHost6)
        btnSingleImagePreview1 = findViewById(R.id.btnSingleImagePreview1)
        btnSingleImagePreview2 = findViewById(R.id.btnSingleImagePreview2)
        btnSingleImagePreview3 = findViewById(R.id.btnSingleImagePreview3)
        btnSingleImagePreview4 = findViewById(R.id.btnSingleImagePreview4)
        btnSingleImagePreview5 = findViewById(R.id.btnSingleImagePreview5)
        btnSingleImagePreview6 = findViewById(R.id.btnSingleImagePreview6)
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
        btnExportFile.setOnClickListener {
            // Convert the filtered image to a PDF
            val pdfFile = Constant.original?.let { it1 -> convertToPdf(it1) }

            if (pdfFile != null) {
                // Save the PDF file using MediaStore
                val pdfUri = savePdfToMediaStore(pdfFile)

                // Start the next activity and pass the PDF URI as an extra
                val intent = Intent(this, AfterExport::class.java)
                intent.putExtra("pdfUri", pdfUri)
//                intent.putExtra("filteredImage", filteredImagePath)
                startActivity(intent)
            }

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
        btnSingleImagePreview6.setOnClickListener {
            val intent = Intent(this,SingleImagePreview::class.java)
            startActivity(intent)
        }
        imageHost.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost2.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost3.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost4.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost5.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        imageHost6.setOnClickListener {
            val intent = Intent(this,Preview::class.java)
            startActivity(intent)
        }
        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }

    private fun copyTrainedDataToStorage() {
        val dataPath = File(filesDir, "tessdata")
        if (!dataPath.exists()) {
            dataPath.mkdirs()
        }

        val language = "eng" // Replace "eng" with the language code for the trained data file you want to use
        val trainedDataFilePath = "$dataPath/$language.traineddata"

        if (!File(trainedDataFilePath).exists()) {
            try {
                val inputStream = assets.open("tessdata/$language.traineddata")
                val outputStream = FileOutputStream(trainedDataFilePath)
                val buffer = ByteArray(1024)
                var bytesRead: Int
                while (inputStream.read(buffer).also { bytesRead = it } != -1) {
                    outputStream.write(buffer, 0, bytesRead)
                }
                outputStream.flush()
                outputStream.close()
                inputStream.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun savePdfToMediaStore(pdfFile: File): Uri? {
        val resolver = contentResolver
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, "filtered_image.pdf")
            put(MediaStore.MediaColumns.MIME_TYPE, "application/pdf")
            put(MediaStore.MediaColumns.RELATIVE_PATH, Environment.DIRECTORY_DOCUMENTS)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            contentValues.put(MediaStore.MediaColumns.IS_PENDING, 1)
        }

        val uri = resolver.insert(MediaStore.Files.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY), contentValues)

        return try {
            uri?.let {
                val outputStream = resolver.openOutputStream(it)
                outputStream?.use { outputStream ->
                    pdfFile.inputStream().use { inputStream ->
                        inputStream.copyTo(outputStream)
                    }
                }

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.clear()
                    contentValues.put(MediaStore.MediaColumns.IS_PENDING, 0)
                    resolver.update(uri, contentValues, null, null)
                }

                uri
            }
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    private fun convertToPdf(bitmap: Bitmap): File? {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(bitmap.width, bitmap.height, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = android.graphics.Paint()
        paint.isAntiAlias = true

        // Calculate the scale to fit the image within the PDF page
        val scaleX = pageInfo.pageWidth.toFloat() / bitmap.width
        val scaleY = pageInfo.pageHeight.toFloat() / bitmap.height
        val scale = minOf(scaleX, scaleY)

        // Calculate the new dimensions of the scaled image
        val scaledWidth = (bitmap.width * scale).toInt()
        val scaledHeight = (bitmap.height * scale).toInt()

        // Calculate the position to center the image on the PDF page
        val offsetX = (pageInfo.pageWidth - scaledWidth) / 2
        val offsetY = (pageInfo.pageHeight - scaledHeight) / 2

        // Create a scaled version of the bitmap
        val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)

        // Draw the scaled image on the canvas
        canvas.drawBitmap(scaledBitmap, offsetX.toFloat(), offsetY.toFloat(), paint)

        pdfDocument.finishPage(page)

        // Save the PDF to internal storage
        val pdfFile = File(filesDir, "filtered_image.pdf")
        return try {
            pdfFile.outputStream().use {
                pdfDocument.writeTo(it)
            }
            pdfDocument.close()
            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        if (isDarkModeEnabled(this)) {
            inflater.inflate(R.menu.top_app_bar_4_dark_mode, menu)
        } else {
            inflater.inflate(R.menu.top_app_bar_4_light_mode, menu)
        }
        return true
    }

    // Function to check if dark mode is enabled
    fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }


}