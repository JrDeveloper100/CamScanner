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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
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
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_export2)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = GridLayoutManager(this, 3) // Set spanCount based on your preference
        val export2Adapter = Export2Adapter(Constant.imageBasket,this)
        recyclerView.adapter = export2Adapter
        btnExportFile = findViewById(R.id.btnExportFile)
        btnExportFile.setOnClickListener {
            if (Constant.conversionType == "Photo"){
                val pdfFile = convertPhotosToPdf(Constant.imageBasket)
                check(pdfFile)
            }else if (Constant.conversionType == "IDCard"){
                val pdfFile = convertToPdf(Constant.imageBasket)
                check(pdfFile)
            }else if (Constant.conversionType == "Document"){
                val pdfFile = convertDocumentToPdf(Constant.imageBasket)
                check(pdfFile)
            }else if (Constant.conversionType == "AcademicCard"){
                val pdfFile = convertAcademicCardToPdf(Constant.imageBasket)
                check(pdfFile)
            }
            // Convert the filtered image to a PDF

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
            put(MediaStore.MediaColumns.DISPLAY_NAME, "ID_Card.pdf")
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

    fun singleImagePreview(position : Int){
        val intent = Intent(this,SingleImagePreview::class.java)
        intent.putExtra("position",position)
        startActivity(intent)
    }

    fun fullScreenPreview() {
        try {
            val intent = Intent(this, Preview::class.java)
            startActivity(intent)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun check(pdfFile: File?){
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

//    private fun convertToPdf(images: ArrayList<Bitmap?>): File? {
//        val pdfDocument = PdfDocument()
//
//        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
//        val pageHeight = (11 * 72).toInt()
//
//        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
//        val page = pdfDocument.startPage(pageInfo)
//        val canvas = page.canvas
//        val paint = android.graphics.Paint()
//        paint.isAntiAlias = true
//
//        val imageCount = images.size
//        val imageWidth = pageWidth / imageCount
//
//        for ((index, image) in images.withIndex()) {
//            val scaledImage = image?.let {
//                val scale = imageWidth.toFloat() / it.width
//                Bitmap.createScaledBitmap(it, imageWidth, (it.height * scale).toInt(), true)
//            }
//
//            val offsetX = index * imageWidth
//            val offsetY = (pageHeight - scaledImage?.height!! ?: 0) / 2
//
//            scaledImage?.let {
//                canvas.drawBitmap(it, offsetX.toFloat(), offsetY.toFloat(), paint)
//            }
//        }
//
//        pdfDocument.finishPage(page)
//
//        val pdfFile = File(filesDir, "ID_Card.pdf")
//        return try {
//            pdfFile.outputStream().use {
//                pdfDocument.writeTo(it)
//            }
//            pdfDocument.close()
//            pdfFile
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }

//    private fun convertToPdf(images: ArrayList<Bitmap?>): File? {
//        val pdfDocument = PdfDocument()
//
//        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
//        val pageHeight = (11 * 72).toInt()
//
//        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
//        val page = pdfDocument.startPage(pageInfo)
//        val canvas = page.canvas
//        val paint = android.graphics.Paint()
//        paint.isAntiAlias = true
//
//        val totalImageHeight = images.sumBy { it?.height ?: 0 }
//        val scale = (pageHeight.toFloat() / totalImageHeight)
//
//        var offsetY = 0
//
//        for (image in images) {
//            val scaledImage = image?.let {
//                Bitmap.createScaledBitmap(it, (it.width * scale).toInt(), (it.height * scale).toInt(), true)
//            }
//
//            scaledImage?.let {
//                canvas.drawBitmap(it, 0f, offsetY.toFloat(), paint)
//                offsetY += it.height
//            }
//        }
//
//        pdfDocument.finishPage(page)
//
//        val pdfFile = File(filesDir, "ID_Card.pdf")
//        return try {
//            pdfFile.outputStream().use {
//                pdfDocument.writeTo(it)
//            }
//            pdfDocument.close()
//            pdfFile
//        } catch (e: Exception) {
//            e.printStackTrace()
//            null
//        }
//    }

    private fun convertToPdf(images: ArrayList<Bitmap?>): File? {
        val pdfDocument = PdfDocument()

        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
        val pageHeight = (11 * 72).toInt()

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = android.graphics.Paint()
        paint.isAntiAlias = true

        val totalImageHeight = images.sumBy { it?.height ?: 0 }
        val scale = (pageHeight.toFloat() / totalImageHeight)

        val centerX = pageWidth / 2

        var offsetY = 0

        for (image in images) {
            val scaledImage = image?.let {
                Bitmap.createScaledBitmap(it, (it.width * scale).toInt(), (it.height * scale).toInt(), true)
            }

            val offsetX = (pageWidth - (scaledImage?.width ?: 0)) / 2

            scaledImage?.let {
                canvas.drawBitmap(it, offsetX.toFloat(), offsetY.toFloat(), paint)
                offsetY += it.height
            }
        }

        pdfDocument.finishPage(page)

        val pdfFile = File(filesDir, "ID_Card.pdf")
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

    private fun convertAcademicCardToPdf(images: ArrayList<Bitmap?>): File? {
        val pdfDocument = PdfDocument()

        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
        val pageHeight = (11 * 72).toInt()

        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        val canvas = page.canvas
        val paint = android.graphics.Paint()
        paint.isAntiAlias = true

        val totalImageHeight = images.sumBy { it?.height ?: 0 }
        val scale = (pageHeight.toFloat() / totalImageHeight)

        val centerX = pageWidth / 2

        var offsetY = 0

        for (image in images) {
            val scaledImage = image?.let {
                Bitmap.createScaledBitmap(it, (it.width * scale).toInt(), (it.height * scale).toInt(), true)
            }

            val offsetX = (pageWidth - (scaledImage?.width ?: 0)) / 2

            scaledImage?.let {
                canvas.drawBitmap(it, offsetX.toFloat(), offsetY.toFloat(), paint)
                offsetY += it.height
            }
        }

        pdfDocument.finishPage(page)

        val pdfFile = File(filesDir, "ID_Card.pdf")
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

    private fun convertPhotosToPdf(images: ArrayList<Bitmap?>): File? {
        val pdfDocument = PdfDocument()

        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
        val pageHeight = (11 * 72).toInt()

        for (image in images) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            val paint = android.graphics.Paint()
            paint.isAntiAlias = true

            val scale = (pageHeight.toFloat() / (image?.height ?: 0))

            val scaledImage = image?.let {
                Bitmap.createScaledBitmap(it, (it.width * scale).toInt(), (it.height * scale).toInt(), true)
            }

            val offsetX = (pageWidth - (scaledImage?.width ?: 0)) / 2
            val offsetY = (pageHeight - (scaledImage?.height ?: 0)) / 2

            scaledImage?.let {
                canvas.drawBitmap(it, offsetX.toFloat(), offsetY.toFloat(), paint)
            }

            pdfDocument.finishPage(page)
        }

        val pdfFile = File(filesDir, "Document.pdf")
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
    private fun convertDocumentToPdf(images: ArrayList<Bitmap?>): File? {
        val pdfDocument = PdfDocument()

        val pageWidth = (8.5 * 72).toInt() // Convert inches to points (1 inch = 72 points)
        val pageHeight = (11 * 72).toInt()

        for (image in images) {
            val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
            val page = pdfDocument.startPage(pageInfo)
            val canvas = page.canvas
            val paint = android.graphics.Paint()
            paint.isAntiAlias = true

            val scale = (pageHeight.toFloat() / (image?.height ?: 0))

            val scaledImage = image?.let {
                Bitmap.createScaledBitmap(it, (it.width * scale).toInt(), (it.height * scale).toInt(), true)
            }

            val offsetX = (pageWidth - (scaledImage?.width ?: 0)) / 2
            val offsetY = (pageHeight - (scaledImage?.height ?: 0)) / 2

            scaledImage?.let {
                canvas.drawBitmap(it, offsetX.toFloat(), offsetY.toFloat(), paint)
            }

            pdfDocument.finishPage(page)
        }

        val pdfFile = File(filesDir, "Document.pdf")
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
    private fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }


}