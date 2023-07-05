package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.fonts.Font
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Stack

class EditDocument : AppCompatActivity() {
    private lateinit var hostLayout : ConstraintLayout
    private val layoutStack : Stack<View> = Stack()
    private lateinit var bottom_sheet_layout_watermark_2 : View
    private lateinit var bottom_sheet_layout_watermark_3 : View
    private lateinit var btnColor : ImageView
    private lateinit var btnFont: ImageView
    private lateinit var toolbar: MaterialToolbar
    private lateinit var btnColorFilter : LinearLayout
    private lateinit var btnAdjust : LinearLayout
    private lateinit var btnHighlight : LinearLayout
    private lateinit var btnPicture : LinearLayout
    private lateinit var btnSignature : LinearLayout
    private lateinit var btnWatermark : LinearLayout
    private lateinit var btnText : LinearLayout
    private lateinit var btnOverlay : LinearLayout
    private lateinit var btnColorEffect : LinearLayout
    private lateinit var bottomSheetLayoutColorFilter : View
    private lateinit var bottomSheetLayoutColorAdjust : View
    private lateinit var bottomSheetLayoutColorHighlight : View
    private lateinit var bottomSheetLayoutColorPicture : View
    private lateinit var bottomSheetLayoutColorSignature : View
    private lateinit var bottomSheetLayoutColorWatermark : View
    private lateinit var bottomSheetLayoutColorText : View
    private lateinit var bottomSheetLayoutColorOverlay : View
    private lateinit var bottomSheetLayoutColorEffect : View
    private lateinit var btnCancel : ImageView
    private lateinit var btnDone : ImageView
    private lateinit var imageView35 : ImageView
    private lateinit var imageView36 : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        bottomSheetLayoutColorFilter = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_filter, null)
        bottomSheetLayoutColorAdjust = layoutInflater.inflate(R.layout.bottom_sheet_layout_adjust, null)
        bottomSheetLayoutColorHighlight = layoutInflater.inflate(R.layout.bottom_sheet_layout_hightlight, null)
        bottomSheetLayoutColorSignature = layoutInflater.inflate(R.layout.bottom_sheet_layout_signature, null)
        bottomSheetLayoutColorWatermark = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark, null)
        bottomSheetLayoutColorText = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_2, null)
        bottomSheetLayoutColorOverlay = layoutInflater.inflate(R.layout.bottom_sheet_layout_overlay, null)
        bottomSheetLayoutColorEffect = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_effect, null)
        btnCancel = bottomSheetLayoutColorFilter.findViewById(R.id.btnCancel)
        btnDone = bottomSheetLayoutColorFilter.findViewById(R.id.btnDone)
        hostLayout = findViewById<ConstraintLayout>(R.id.hostLayout)
        bottom_sheet_layout_watermark_2 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_2, null)
        bottom_sheet_layout_watermark_3 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_3, null)
        btnColor = bottom_sheet_layout_watermark_2.findViewById(R.id.btnColor)
        btnFont = bottom_sheet_layout_watermark_3.findViewById(R.id.btnFont)

        imageView35 = bottomSheetLayoutColorAdjust.findViewById(R.id.imageView35)
        imageView36 = bottomSheetLayoutColorAdjust.findViewById(R.id.imageView36)

        btnColorFilter = findViewById(R.id.btnColorFilter)
        btnAdjust = findViewById(R.id.btnAdjust)
        btnHighlight = findViewById(R.id.btnHighlight)
        btnPicture = findViewById(R.id.btnPicture)
        btnSignature = findViewById(R.id.btnSignature)
        btnWatermark = findViewById(R.id.btnWatermark)
        btnText = findViewById(R.id.btnText)
        btnOverlay = findViewById(R.id.btnOverlay)
        btnColorEffect = findViewById(R.id.btnColorEffect)

        btnColorFilter.setOnClickListener {
            replaceLayout(bottomSheetLayoutColorFilter)
        }
        btnCancel.setOnClickListener {
            if (layoutStack.size > 1){
                layoutStack.pop()
                val previousLayout = layoutStack.peek()
                replaceLayout(previousLayout)
            }
        }
        btnAdjust.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_adjust, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnHighlight.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_hightlight, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnPicture.setOnClickListener {
            val intent = Intent(this, Gallery::class.java)
            startActivity(intent)
        }
        btnSignature.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_signature, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnWatermark.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnText.setOnClickListener {
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(bottom_sheet_layout_watermark_2)
            dialog.show()
        }
        btnOverlay.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_overlay, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnColorEffect.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_effect, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnColor.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_3, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }
        btnFont.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_4, null)
            val dialog = BottomSheetDialog(this)
            dialog.setContentView(view)
            dialog.show()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
            btnColorFilter.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnAdjust.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnHighlight.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnPicture.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnSignature.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnWatermark.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnText.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnOverlay.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnColorEffect.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnDone.setImageResource(R.drawable.done_icon_dark_mode)
            btnCancel.setImageResource(R.drawable.close_icon_dark_mode)
            imageView35.setImageResource(R.drawable.close_icon_dark_mode)
            imageView36.setImageResource(R.drawable.done_icon_dark_mode)


        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
            btnColorFilter.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnAdjust.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnHighlight.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnPicture.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnSignature.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnWatermark.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnText.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnOverlay.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
            btnColorEffect.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)

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

    // Function to check if dark mode is enabled
    fun isDarkModeEnabled(context: Context): Boolean {
        val configuration = context.resources.configuration
        return (configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK) == Configuration.UI_MODE_NIGHT_YES
    }

    private fun replaceLayout(newLayout: View) {
        hostLayout.removeAllViews()
        hostLayout.addView(newLayout)
        layoutStack.push(newLayout)

    }
}