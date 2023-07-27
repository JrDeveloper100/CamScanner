package com.example.camscanner

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.appbar.MaterialToolbar
import ja.burhanrashid52.photoeditor.PhotoEditorView
import java.util.Stack

class EditDocument : AppCompatActivity() {
    private lateinit var hostLayout : ConstraintLayout
    private val layoutStack : Stack<View> = Stack()
    private lateinit var bottom_sheet_layout_watermark_2 : View
    private lateinit var bottom_sheet_layout_watermark_3 : View
    private lateinit var bottom_sheet_layout_watermark_4 : View
    private lateinit var btnColor : LinearLayout
    private lateinit var btnFont: LinearLayout
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
    private lateinit var bottomSheetLayoutOverlay : View
    private lateinit var bottomSheetLayoutColorEffect : View
    private lateinit var btnCancel : ImageView
    private lateinit var btnDone : ImageView
    private lateinit var imageView35 : ImageView
    private lateinit var imageView36 : ImageView
    private lateinit var imageView3535 : ImageView
    private lateinit var imageView3636 : ImageView
    private lateinit var imageView353535 : ImageView
    private lateinit var imageView363636 : ImageView
    private lateinit var imageView35353535 : ImageView
    private lateinit var imageView36363636 : ImageView
    private lateinit var btnCross : ImageView
    private lateinit var btnTick : ImageView
    private lateinit var btnWatermark3Cross : ImageView
    private lateinit var btnWatermark3Tick : ImageView
    private lateinit var btnWatermark4Cross : ImageView
    private lateinit var btnWatermark4Tick : ImageView
    private lateinit var btnOverlayCross : ImageView
    private lateinit var btnOverlayTick : ImageView
    private lateinit var btnColorEffectCross : ImageView
    private lateinit var btnColorEffectTick : ImageView
    private lateinit var btnBrightness : LinearLayout
    private lateinit var btnContrast : LinearLayout
    private lateinit var btnSaturation : LinearLayout
    private lateinit var btnExposure : LinearLayout
    private lateinit var btnHighlightInner : LinearLayout
    private lateinit var btnEraser : LinearLayout
    private lateinit var btnColorInner : LinearLayout
    private lateinit var btnFonts : LinearLayout
    private lateinit var btnColors : LinearLayout
    private lateinit var btnOpacity : LinearLayout
    private lateinit var btnWatermark2Fonts : LinearLayout
    private lateinit var btnWatermark2Colors : LinearLayout
    private lateinit var btnWatermark2Opacity : LinearLayout
    private lateinit var btnWatermark3Fonts : LinearLayout
    private lateinit var btnWatermark3Colors : LinearLayout
    private lateinit var btnWatermark3Opacity : LinearLayout
    private lateinit var btnWatermark4Fonts : LinearLayout
    private lateinit var btnWatermark4Colors : LinearLayout
    private lateinit var btnWatermark4Opacity : LinearLayout
    private lateinit var photoEditorView : PhotoEditorView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val filteredImagePath = intent.getStringExtra("filteredImagePath")
        val bitmapImage = BitmapFactory.decodeFile(filteredImagePath)

        bottomSheetLayoutColorFilter = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_filter, null)
        bottomSheetLayoutColorAdjust = layoutInflater.inflate(R.layout.bottom_sheet_layout_adjust, null)
        bottomSheetLayoutColorHighlight = layoutInflater.inflate(R.layout.bottom_sheet_layout_hightlight, null)
        bottomSheetLayoutColorSignature = layoutInflater.inflate(R.layout.bottom_sheet_layout_signature, null)
        bottomSheetLayoutColorWatermark = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark, null)
        bottomSheetLayoutColorText = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_2, null)
        bottomSheetLayoutOverlay = layoutInflater.inflate(R.layout.bottom_sheet_layout_overlay, null)
        bottomSheetLayoutColorEffect = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_effect, null)
        btnCancel = bottomSheetLayoutColorFilter.findViewById(R.id.btnCancel)
        btnDone = bottomSheetLayoutColorFilter.findViewById(R.id.btnDone)
//        hostLayout = findViewById<ConstraintLayout>(R.id.hostLayout)
        bottom_sheet_layout_watermark_2 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_2, null)
        bottom_sheet_layout_watermark_3 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_3, null)
        bottom_sheet_layout_watermark_4 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_4, null)
        btnColor = bottom_sheet_layout_watermark_2.findViewById(R.id.btnColor)
        btnFont = bottom_sheet_layout_watermark_2.findViewById(R.id.btnFont)
        btnFonts = bottomSheetLayoutColorWatermark.findViewById(R.id.btnFonts)
        btnColors = bottomSheetLayoutColorWatermark.findViewById(R.id.btnColors)
        btnOpacity = bottomSheetLayoutColorWatermark.findViewById(R.id.btnOpacity)
        btnWatermark2Fonts = bottom_sheet_layout_watermark_2.findViewById(R.id.btnWatermark2Fonts)
        btnWatermark2Colors = bottom_sheet_layout_watermark_2.findViewById(R.id.btnWatermark2Colors)
        btnWatermark2Opacity = bottom_sheet_layout_watermark_2.findViewById(R.id.btnWatermark2Opacity)
        btnWatermark3Fonts = bottom_sheet_layout_watermark_3.findViewById(R.id.btnWatermark3Fonts)
        btnWatermark3Colors = bottom_sheet_layout_watermark_3.findViewById(R.id.btnWatermark3Colors)
        btnWatermark3Opacity = bottom_sheet_layout_watermark_3.findViewById(R.id.btnWatermark3Opacity)
        btnWatermark4Fonts = bottom_sheet_layout_watermark_4.findViewById(R.id.btnWatermark4Fonts)
        btnWatermark4Colors = bottom_sheet_layout_watermark_4.findViewById(R.id.btnWatermark4Colors)
        btnWatermark4Opacity = bottom_sheet_layout_watermark_4.findViewById(R.id.btnWatermark4Opacity)

        imageView35 = bottomSheetLayoutColorAdjust.findViewById(R.id.imageView35)
        imageView36 = bottomSheetLayoutColorAdjust.findViewById(R.id.imageView36)
        imageView3535 = bottomSheetLayoutColorHighlight.findViewById(R.id.imageView3535)
        imageView3636 = bottomSheetLayoutColorHighlight.findViewById(R.id.imageView3636)
        imageView353535 = bottomSheetLayoutColorSignature.findViewById(R.id.imageView353535)
        imageView363636 = bottomSheetLayoutColorSignature.findViewById(R.id.imageView363636)
        imageView35353535 = bottomSheetLayoutColorWatermark.findViewById(R.id.imageView35353535)
        imageView36363636 = bottomSheetLayoutColorWatermark.findViewById(R.id.imageView36363636)
        btnCross = bottom_sheet_layout_watermark_2.findViewById(R.id.btnCross)
        btnTick = bottom_sheet_layout_watermark_2.findViewById(R.id.btnTick)
        btnWatermark3Cross = bottom_sheet_layout_watermark_3.findViewById(R.id.btnWatermark3Cross)
        btnWatermark3Tick = bottom_sheet_layout_watermark_3.findViewById(R.id.btnWatermark3Tick)
        btnWatermark4Cross = bottom_sheet_layout_watermark_4.findViewById(R.id.btnWatermark4Cross)
        btnWatermark4Tick = bottom_sheet_layout_watermark_4.findViewById(R.id.btnWatermark4Tick)
        btnOverlayCross = bottomSheetLayoutOverlay.findViewById(R.id.btnOverlayCross)
        btnOverlayTick = bottomSheetLayoutOverlay.findViewById(R.id.btnOverlayTick)
        btnColorEffectCross = bottomSheetLayoutColorEffect.findViewById(R.id.btnColorEffectCross)
        btnColorEffectTick = bottomSheetLayoutColorEffect.findViewById(R.id.btnColorEffectTick)
        btnBrightness = bottomSheetLayoutColorAdjust.findViewById(R.id.btnBrightness)
        btnContrast = bottomSheetLayoutColorAdjust.findViewById(R.id.btnContrast)
        btnSaturation = bottomSheetLayoutColorAdjust.findViewById(R.id.btnSaturation)
        btnExposure = bottomSheetLayoutColorAdjust.findViewById(R.id.btnExposure)
        btnHighlightInner = bottomSheetLayoutColorHighlight.findViewById(R.id.btnHighlightInner)
        btnEraser = bottomSheetLayoutColorHighlight.findViewById(R.id.btnEraser)
        btnColorInner = bottomSheetLayoutColorHighlight.findViewById(R.id.btnColorInner)
        photoEditorView = findViewById(R.id.photoEditorView)
        photoEditorView.source.setImageBitmap(bitmapImage)

//        btnColorFilter = findViewById(R.id.btnColorFilter)
//        btnAdjust = findViewById(R.id.btnAdjust)
//        btnHighlight = findViewById(R.id.btnHighlight)
//        btnPicture = findViewById(R.id.btnPicture)
//        btnSignature = findViewById(R.id.btnSignature)
//        btnWatermark = findViewById(R.id.btnWatermark)
//        btnText = findViewById(R.id.btnText)
//        btnOverlay = findViewById(R.id.btnOverlay)
//        btnColorEffect = findViewById(R.id.btnColorEffect)

//        btnColorFilter.setOnClickListener {
////            replaceLayout(bottomSheetLayoutColorFilter)
//            replaceFragments(savedInstanceState, ColorFilterFragment())
//        }
//        btnCancel.setOnClickListener {
//            if (layoutStack.size > 1){
//                layoutStack.pop()
//                val previousLayout = layoutStack.peek()
//                replaceLayout(previousLayout)
//            }
//        }
//        btnAdjust.setOnClickListener {
////            replaceLayout(bottomSheetLayoutColorAdjust)
//            replaceFragments(savedInstanceState, AdjustFragment())
//        }
//        btnHighlight.setOnClickListener {
////            replaceLayout(bottomSheetLayoutColorHighlight)
//            replaceFragments(savedInstanceState, HighlightFragment())
//        }
//        btnPicture.setOnClickListener {
//            val intent = Intent(this, Gallery::class.java)
//            startActivity(intent)
//        }
//        btnSignature.setOnClickListener {
////            replaceLayout(bottomSheetLayoutColorSignature)
//            replaceFragments(savedInstanceState, SignatureFragment())
//        }
//        btnWatermark.setOnClickListener {
////            replaceLayout(bottomSheetLayoutColorWatermark)
//            replaceFragments(savedInstanceState, WatermarkFragment())
//        }
//        btnText.setOnClickListener {
//            replaceLayout(bottom_sheet_layout_watermark_2)
//        }
//        btnOverlay.setOnClickListener {
//            replaceLayout(bottomSheetLayoutOverlay)
//        }
//        btnColorEffect.setOnClickListener {
//            replaceLayout(bottomSheetLayoutColorEffect)
//        }
//        btnColor.setOnClickListener {
//            replaceLayout(bottom_sheet_layout_watermark_3)
//        }
//        btnFont.setOnClickListener {
//            replaceLayout(bottom_sheet_layout_watermark_4)
//        }

//        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
//        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
//            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
//            btnColorFilter.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnAdjust.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnHighlight.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnPicture.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnSignature.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnWatermark.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnText.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnOverlay.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnColorEffect.setBackgroundResource(R.drawable.design_with_ripple_effect_dark_mode)
//            btnDone.setImageResource(R.drawable.done_icon_dark_mode)
//            btnCancel.setImageResource(R.drawable.close_icon_dark_mode)
//            imageView35.setImageResource(R.drawable.close_icon_dark_mode)
//            imageView36.setImageResource(R.drawable.done_icon_dark_mode)
//            btnBrightness.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnContrast.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnSaturation.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnExposure.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnHighlightInner.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnEraser.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnColorInner.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            imageView3535.setImageResource(R.drawable.close_icon_dark_mode)
//            imageView3636.setImageResource(R.drawable.done_icon_dark_mode)
//            imageView353535.setImageResource(R.drawable.close_icon_dark_mode)
//            imageView363636.setImageResource(R.drawable.done_icon_dark_mode)
//            imageView35353535.setImageResource(R.drawable.close_icon_dark_mode)
//            imageView36363636.setImageResource(R.drawable.done_icon_dark_mode)
//            btnTick.setImageResource(R.drawable.done_icon_dark_mode)
//            btnCross.setImageResource(R.drawable.close_icon_dark_mode)
//            btnWatermark3Tick.setImageResource(R.drawable.done_icon_dark_mode)
//            btnWatermark3Cross.setImageResource(R.drawable.close_icon_dark_mode)
//            btnWatermark4Tick.setImageResource(R.drawable.done_icon_dark_mode)
//            btnWatermark4Cross.setImageResource(R.drawable.close_icon_dark_mode)
//            btnOverlayTick.setImageResource(R.drawable.done_icon_dark_mode)
//            btnOverlayCross.setImageResource(R.drawable.close_icon_dark_mode)
//            btnColorEffectTick.setImageResource(R.drawable.done_icon_dark_mode)
//            btnColorEffectCross.setImageResource(R.drawable.close_icon_dark_mode)
//            btnFonts.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnColors.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnOpacity.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark2Fonts.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark2Colors.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark2Opacity.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark3Fonts.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark3Colors.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark3Opacity.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark4Fonts.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark4Colors.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//            btnWatermark4Opacity.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
//
//        }else{
//            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
//            btnColorFilter.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnAdjust.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnHighlight.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnPicture.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnSignature.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnWatermark.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnText.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnOverlay.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnColorEffect.setBackgroundResource(R.drawable.design_with_ripple_effect_light_mode)
//            btnBrightness.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnContrast.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnSaturation.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnExposure.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnHighlightInner.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnEraser.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnColorInner.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnFonts.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnColors.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnOpacity.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark2Fonts.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark2Colors.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark2Opacity.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark3Fonts.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark3Colors.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark3Opacity.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark4Fonts.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark4Colors.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//            btnWatermark4Opacity.setBackgroundResource(R.drawable.edit_document_bottom_icon_design_light_mode)
//        }

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