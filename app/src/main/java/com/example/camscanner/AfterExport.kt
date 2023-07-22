package com.example.camscanner

import android.content.ContentResolver
import android.content.ContentValues
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfWriter
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class AfterExport : AppCompatActivity() {

    private lateinit var toolbar : MaterialToolbar
    private lateinit var btnShare : LinearLayout
    private lateinit var shareBottomSheet : View
    private lateinit var passwordBottomSheet : View
    private lateinit var btnShareAsImage : LinearLayout
    private lateinit var btnShareAsPdf : LinearLayout
    private lateinit var btnSharePdfWithPassword : LinearLayout
    private lateinit var btnSharePdfWithPasswordButton : LinearLayout
    private lateinit var delete : LinearLayout
    private lateinit var mail : LinearLayout
    private lateinit var share : LinearLayout
    private lateinit var preview : LinearLayout
    private lateinit var btnPreview : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_export)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnPreview = findViewById(R.id.btnPreview)
        shareBottomSheet = layoutInflater.inflate(R.layout.share_bottom_sheet, null)
        passwordBottomSheet = layoutInflater.inflate(R.layout.password_bottom_sheet, null)
        btnShareAsImage = shareBottomSheet.findViewById(R.id.btnShareAsImage)
        btnShareAsPdf = shareBottomSheet.findViewById(R.id.btnShareAsPdf)
        btnSharePdfWithPassword = shareBottomSheet.findViewById(R.id.btnSharePdfWithPassword)
        btnSharePdfWithPasswordButton = shareBottomSheet.findViewById(R.id.btnSharePdfWithPasswordButton)
        delete = findViewById(R.id.delete)
        mail = findViewById(R.id.mail)
        share = findViewById(R.id.share)
        preview = findViewById(R.id.preview)

        btnShare = findViewById(R.id.btnShare)
        btnShare.setOnClickListener {
            openBottomSheet()
        }
        btnSharePdfWithPasswordButton.setOnClickListener {
            openPasswordBottomSheet()
        }
        btnShareAsImage.setOnClickListener {
            val imageUri = intent.getParcelableExtra<Uri>("filteredImage")
            imageUri?.let {
                shareImage(it)
            }
        }
        btnShareAsPdf.setOnClickListener {
            val pdfUri = intent.getParcelableExtra<Uri>("pdfUri")
            pdfUri?.let {
                sharePdf(it)
            }
        }
        btnPreview.setOnClickListener {
            val pdfUri = intent.getParcelableExtra<Uri>("pdfUri")
            pdfUri?.let {
                openPreview(it)
            }
        }


        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
            btnShareAsImage.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnShareAsPdf.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnSharePdfWithPassword.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            delete.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            mail.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            share.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            preview.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
            btnShareAsImage.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnShareAsPdf.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnSharePdfWithPassword.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            delete.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            mail.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            share.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            preview.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
        }

    }

    private fun shareImage(imageUri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_STREAM, imageUri)
        startActivity(Intent.createChooser(intent, "Share Image"))
    }

    private fun sharePdf(pdfUri: Uri) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "application/pdf"
        intent.putExtra(Intent.EXTRA_STREAM, pdfUri)
        startActivity(Intent.createChooser(intent, "Share PDF"))
    }

    private fun openPreview(pdfUri: Uri) {
        val intent = Intent(this, FilePreview::class.java)
        intent.putExtra("pdfUri", pdfUri)
        startActivity(intent)
    }

    private fun openPasswordBottomSheet() {
        val dialog = BottomSheetDialog(this)
        if (passwordBottomSheet.parent != null) {
            (passwordBottomSheet.parent as ViewGroup).removeView(passwordBottomSheet)
        }
        dialog.setContentView(passwordBottomSheet)
        dialog.setCancelable(true)

        val password = dialog.findViewById<EditText>(R.id.password)
        val confirmPassword = dialog.findViewById<EditText>(R.id.confirmPassword)
        val imageUri = intent.getParcelableExtra<Bitmap>("filteredImage")
        dialog.findViewById<Button>(R.id.btnSetPassword)?.setOnClickListener {
            val pass = password?.text.toString()
            val confirmPass = confirmPassword?.text.toString()
            if (pass.isEmpty()) {
                Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
            } else {
                // Convert the filtered image to a password-protected PDF
                val pdfFileWithPassword =
                    imageUri?.let { it1 -> convertToPasswordProtectedPdf(it1, pass) }

                if (pdfFileWithPassword != null) {
                    // Save the password-protected PDF file using MediaStore
                    val pdfUri = savePdfToMediaStore(pdfFileWithPassword)

                    // Start the next activity and pass the PDF URI as an extra
                    val intent = Intent(this, AfterExport::class.java)
                    intent.putExtra("pdfUri", pdfUri)
                    startActivity(intent)
                }

                dialog.dismiss()
            }

        }
        dialog.show()

        val themeMode =
            resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if (themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            dialog.findViewById<EditText>(R.id.password)
                ?.setHintTextColor(resources.getColor(R.color.white))
            dialog.findViewById<EditText>(R.id.confirmPassword)
                ?.setHintTextColor(resources.getColor(R.color.white))
        }

    }

    private fun savePdfToMediaStore(pdfFile: File): Uri? {
        val resolver: ContentResolver = contentResolver
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

    private fun convertToPasswordProtectedPdf(bitmap: Bitmap, password: String?): File? {
        val pdfFile = File(filesDir, "filtered_image_with_password.pdf")
        val document = Document()

        return try {
            PdfWriter.getInstance(document, FileOutputStream(pdfFile)).apply {
                if (password != null) {
                    setEncryption(password.toByteArray(), password.toByteArray(),
                        PdfWriter.ALLOW_PRINTING or PdfWriter.ALLOW_COPY, PdfWriter.ENCRYPTION_AES_128)
                }
            }

            document.open()
            val pageWidth = document.pageSize.width
            val pageHeight = document.pageSize.height

            // Calculate the scale to fit the image within the PDF page
            val scaleX = pageWidth / bitmap.width.toFloat()
            val scaleY = pageHeight / bitmap.height.toFloat()
            val scale = minOf(scaleX, scaleY)

            // Calculate the new dimensions of the scaled image
            val scaledWidth = (bitmap.width * scale).toInt()
            val scaledHeight = (bitmap.height * scale).toInt()

            // Calculate the position to center the image on the PDF page
            val offsetX = (pageWidth - scaledWidth) / 2
            val offsetY = (pageHeight - scaledHeight) / 2

            // Create a scaled version of the bitmap
            val scaledBitmap = Bitmap.createScaledBitmap(bitmap, scaledWidth, scaledHeight, true)

            // Add the scaled image to the PDF
            val byteArrayOutputStream = ByteArrayOutputStream()
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val image = Image.getInstance(byteArray)
            image.setAbsolutePosition(offsetX.toFloat(), offsetY.toFloat())
            image.scaleToFit(scaledWidth.toFloat(), scaledHeight.toFloat())

            document.add(image)
            document.close()

            pdfFile
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(this)
        if (shareBottomSheet.parent != null) {
            (shareBottomSheet.parent as ViewGroup).removeView(shareBottomSheet)
        }
        dialog.setContentView(shareBottomSheet)
        dialog.setCancelable(true)
        dialog.show()
    }
}