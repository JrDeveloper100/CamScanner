package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import com.xiaopo.flying.sticker.StickerView
import ja.burhanrashid52.photoeditor.PhotoEditorView
import java.util.Stack

class EditDocument : AppCompatActivity() {
    private lateinit var toolbar: MaterialToolbar
    private lateinit var photoEditorView : PhotoEditorView
    private lateinit var stickerView : StickerView
    private lateinit var imageLayout : ConstraintLayout
    private lateinit var watermarkText : EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        photoEditorView = findViewById(R.id.photoEditorView)
        stickerView = findViewById(R.id.stickerView)
        photoEditorView.source.setImageBitmap(Constant.original)
        imageLayout = findViewById(R.id.imageLayout)
        watermarkText = findViewById(R.id.watermarkText)

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
            watermarkText.setBackgroundResource(R.drawable.white_bg_dark_mode)
        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
            watermarkText.setBackgroundResource(R.drawable.white_bg)
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
                goToActivity()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun goToActivity() {
        stickerView.isConstrained = false
        stickerView.isLocked = true
         Constant.original = getMainFrameBitmap()
//        Constant.original = (photoEditorView.source.drawable as BitmapDrawable).bitmap
        finish()
    }

    private fun getMainFrameBitmap(): Bitmap? {
        val createBitmap = Bitmap.createBitmap(imageLayout.width, imageLayout.height, Bitmap.Config.ARGB_8888)
        imageLayout.draw(Canvas(createBitmap))
        return imageBitmap(createBitmap)
    }

    private fun imageBitmap(bitmap: Bitmap) : Bitmap? {

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

    // Function to check if dark mode is enabled
    private fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

}