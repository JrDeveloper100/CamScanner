package com.example.camscanner

import android.app.Dialog
import android.content.ContentResolver
import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.text.InputType
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.FileProvider
import com.github.barteksc.pdfviewer.BuildConfig
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.itextpdf.text.Document
import com.itextpdf.text.Image
import com.itextpdf.text.pdf.PdfReader
import com.itextpdf.text.pdf.PdfStamper
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
    private lateinit var btnEmail : LinearLayout
    private lateinit var btnDelete : LinearLayout
    private lateinit var textView16 : TextView
    private var showPassword = false

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
        btnEmail = findViewById(R.id.btnEmail)
        btnDelete = findViewById(R.id.btnDelete)
        textView16 = findViewById(R.id.textView16)
//        val filteredImagePath = intent.getStringExtra("filteredImage")
//        val bitmapImage = BitmapFactory.decodeFile(filteredImagePath)
//        val imageByteArray = bitmapToByteArray(bitmapImage)
        val filePath = intent.getStringExtra("file_path")
        textView16.text = Constant.fileName
        btnShare.setOnClickListener {
            openBottomSheet()
        }
        btnSharePdfWithPasswordButton.setOnClickListener {
            openPasswordBottomSheet(filePath)
        }
        btnShareAsImage.setOnClickListener {
            shareImage()
        }
        btnShareAsPdf.setOnClickListener {
//            val pdfUri = intent.getParcelableExtra<Uri>("pdfUri")
//            pdfUri?.let {
//                sharePdf(it)
//            }
            shareAsPDF(filePath)
        }
        btnPreview.setOnClickListener {
//            val pdfUri = intent.getParcelableExtra<Uri>("pdfUri")
//            pdfUri?.let {
//                openPreview(it)
//            }
            displayPDF(filePath)
        }
        btnEmail.setOnClickListener {
            showEmailDialog(filePath)
        }
        btnDelete.setOnClickListener {
            val file = File(filePath.toString())
            val builder: AlertDialog.Builder = AlertDialog.Builder(this)
            builder.setMessage("Are you sure you want to delete?")
                .setPositiveButton("Confirm", DialogInterface.OnClickListener { dialog, id ->
                    if (file.exists()) {
                        file.delete()
                        Toast.makeText(this, "File deleted", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this, Home::class.java)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                })
                .setNegativeButton("No", DialogInterface.OnClickListener { dialog, id ->
                    // User cancelled the dialog
                    dialog.dismiss()
                })
            val dialog: AlertDialog = builder.create()
            dialog.setOnShowListener {
                // Get a reference to the positive/negative button
                val positiveButton = dialog.getButton(AlertDialog.BUTTON_POSITIVE)
                val negativeButton = dialog.getButton(AlertDialog.BUTTON_NEGATIVE)

                // Set the text color of the positive/negative button
                positiveButton.setTextColor(resources.getColor(R.color.red))
                negativeButton.setTextColor(resources.getColor(R.color.black))

                // Show the dialog
                dialog.show()
            }
            dialog.show()
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

    private fun showEmailDialog(filePath: String?) {
        val file = File(filePath.toString())
        val uri = FileProvider.getUriForFile(
            this,
            "${this.packageName}.fileprovider",
            file
        )
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.email_custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val buttonCancel =   dialog.findViewById<LinearLayout>(R.id.buttonCancel)
        val email = dialog.findViewById<EditText>(R.id.email)
        val btnEmail = dialog.findViewById<Button>(R.id.btnEmail)
        dialog.setCancelable(true)
        dialog.show()
        btnEmail.setOnClickListener {
            val emailAddress = email.text.toString()
            if (emailAddress.isNotEmpty()){
                if (android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()){
                    val emailIntent = Intent(Intent.ACTION_SEND).apply {
                        type = "application/pdf"
                        putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
                        putExtra(Intent.EXTRA_SUBJECT, "PDF File")
                        putExtra(Intent.EXTRA_STREAM, uri)
                        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                        `package` = "com.google.android.gm"
                    }
                    startActivity(Intent.createChooser(emailIntent, "Send Email"))
                }else{
                    Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Provide Valid Email", Toast.LENGTH_SHORT).show()
            }

        }
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            buttonCancel.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            email.setHintTextColor(resources.getColor(R.color.white))
        }else{
            buttonCancel.setBackgroundResource(R.drawable.cancel_icon_design_light_mode)
        }

    }

    private fun shareAsPDF(filePath: String?) {
        val file = File(filePath.toString())
        println("::::::::::::::::::PDF PATH::::::::::::${filePath.toString()}:::::::::::::::::::::::::::::::")
        if (file.exists()) {
            val uri = FileProvider.getUriForFile(
                this,
                "${this.packageName}.fileprovider",
                file
            )

            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "application/pdf"
                putExtra(Intent.EXTRA_STREAM, uri)
            }

            val chooserIntent = Intent.createChooser(shareIntent, "Share File")
            startActivity(chooserIntent)
        } else {
            Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayPDF(path: String?) {
        val intent = Intent(this, FilePreview::class.java)
        intent.putExtra("path", path)
        startActivity(intent)
    }

    private fun bitmapToByteArray(bitmap: Bitmap): ByteArray {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        return stream.toByteArray()
    }

    private fun convertBitmapToUri(bitmap: Bitmap?): Uri? {

        val contentResolver: ContentResolver = contentResolver

        // Save the bitmap as an image file using the cache directory
        val imageFile = File(cacheDir, "filtered_image.jpg")
        try {
            val outputStream = FileOutputStream(imageFile)
            bitmap?.compress(Bitmap.CompressFormat.JPEG, 100, outputStream)
            outputStream.flush()
            outputStream.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        // Generate a content URI for the image file using FileProvider
        return FileProvider.getUriForFile(this, "${BuildConfig.APPLICATION_ID}.fileprovider", imageFile)

    }

    private fun shareImage() {
        val shareIntent = Intent(Intent.ACTION_SEND_MULTIPLE).apply {
            type = "image/jpeg"
            val imageUris = ArrayList<Uri>()

            // Convert image paths in the imageList to URIs
            for (imagePath in Constant.imagesPath) {
                // Convert the imagePath to a regular file path
                val filePath = imagePath.toString().removePrefix("file://")
                val imageFile = File(filePath)
                val imageUri = FileProvider.getUriForFile(this@AfterExport, "com.example.camscanner.firstfileprovider", imageFile)
                imageUris.add(imageUri)
            }
            // Attach the list of content URIs
            putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris)

        }
        // Optionally set a title for the sharing chooser
        val chooser = Intent.createChooser(shareIntent, "Share Images")

        // Start the sharing chooser
        startActivity(chooser)
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

    private fun openPasswordBottomSheet(filePath: String?) {
        val dialog = BottomSheetDialog(this)
        if (passwordBottomSheet.parent != null) {
            (passwordBottomSheet.parent as ViewGroup).removeView(passwordBottomSheet)
        }
        dialog.setContentView(passwordBottomSheet)
        dialog.setCancelable(true)
        val password = dialog.findViewById<EditText>(R.id.password)
        val confirmPassword = dialog.findViewById<EditText>(R.id.confirmPassword)
        password?.setText("")
        confirmPassword?.setText("")
        dialog.findViewById<Button>(R.id.btnSetPassword)?.setOnClickListener {
                val pass = password?.text.toString()
                val confirmPass = confirmPassword?.text.toString()
                if (pass.isEmpty() || confirmPass.isEmpty()) {
                    Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show()
                } else if (pass!=confirmPass){
                    Toast.makeText(this, "Password Doesn't Match", Toast.LENGTH_SHORT).show()
                }
                else {
                    // Convert the filtered image to a password-protected PDF
                    setPasswordToPdf(pass)
                    val file = File(applicationContext.filesDir, "${Constant.folderName}/${Constant.fileNameWithoutExtension}")
                    if (file.exists()) {
                        val uri = FileProvider.getUriForFile(
                            this,
                            "${this.packageName}.fileprovider",
                            file
                        )

                        val shareIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "application/pdf"
                            putExtra(Intent.EXTRA_STREAM, uri)
                        }

                        val chooserIntent = Intent.createChooser(shareIntent, "Share File")
                        startActivity(chooserIntent)
                    } else {
                        Toast.makeText(this, "File not found", Toast.LENGTH_SHORT).show()
                    }
                    dialog.dismiss()
                }

        }
        dialog.findViewById<TextView>(R.id.btnNotNow)?.setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<ImageView>(R.id.passOneShowHide)?.setOnClickListener {
            toggleVisibilityOne()
        }
        dialog.findViewById<ImageView>(R.id.confirmPassShowHide)?.setOnClickListener {
            toggleVisibilityTwo()
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

    private fun setPasswordToPdf(pass: String) {
        val file = File(applicationContext.filesDir, "${Constant.folderName}/${Constant.fileName}")
        if (file.exists()){
            try {
                val fileNameWithoutExtension = Constant.fileName.replaceFirst(" ", " (Ency) ")
                val inputFile = File(applicationContext.filesDir, "${Constant.folderName}/${Constant.fileName}")
                val outputFile = File(applicationContext.filesDir, "${Constant.folderName}/${fileNameWithoutExtension}")
                Constant.fileNameWithoutExtension = fileNameWithoutExtension
                val reader = PdfReader(inputFile.absolutePath)
                val stamper = PdfStamper(reader, FileOutputStream(outputFile))
                stamper.setEncryption(
                    pass.toByteArray(),
                    pass.toByteArray(),
                    PdfWriter.ALLOW_PRINTING, // Modify permissions as needed
                    PdfWriter.ENCRYPTION_AES_128
                )
                stamper.close()
                reader.close()

                Toast.makeText(this, "PDF encrypted successfully", Toast.LENGTH_SHORT).show()
                // Update the adapter or UI if needed
            }catch (e: java.lang.Exception){
                Toast.makeText(this, "PDF encryption failed: ${e.message}", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleVisibilityTwo() {
        if (showPassword){
            passwordBottomSheet.findViewById<ImageView>(R.id.confirmPassShowHide).setImageResource(R.drawable.visibility_off_icon)
            passwordBottomSheet.findViewById<EditText>(R.id.confirmPassword).inputType = 129
            showPassword = false
        }else{
            passwordBottomSheet.findViewById<ImageView>(R.id.confirmPassShowHide).setImageResource(R.drawable.visibility_on_icon)
            passwordBottomSheet.findViewById<EditText>(R.id.confirmPassword).inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            showPassword = true
        }
    }

    private fun toggleVisibilityOne() {
        if (showPassword){
            passwordBottomSheet.findViewById<ImageView>(R.id.passOneShowHide).setImageResource(R.drawable.visibility_off_icon)
            passwordBottomSheet.findViewById<EditText>(R.id.password).inputType = 129
            showPassword = false
        }else{
            passwordBottomSheet.findViewById<ImageView>(R.id.passOneShowHide).setImageResource(R.drawable.visibility_on_icon)
            passwordBottomSheet.findViewById<EditText>(R.id.password).inputType = InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD
            showPassword = true
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
        val byteArrayOutputStream = ByteArrayOutputStream()

        return try {
            PdfWriter.getInstance(document, FileOutputStream(pdfFile)).apply {
                if (password != null) {
                    setEncryption(
                        password.toByteArray(),
                        password.toByteArray(),
                        2052,
                        2
                    )
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
            scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
            val byteArray = byteArrayOutputStream.toByteArray()
            val image = Image.getInstance(byteArray)
            image.setAbsolutePosition(0f, 0f) // Set the image position to (0, 0)
            image.scaleToFit(pageWidth, pageHeight) // Scale the image to fit the entire page

            document.add(image)
            document.close()

            byteArrayOutputStream.close() // Close the ByteArrayOutputStream to release resources

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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed() // Go back to the previous activity
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}