package com.example.camscanner

import android.content.Intent
import android.graphics.fonts.Font
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Stack

class EditDocument : AppCompatActivity() {
    private lateinit var hostLayout : ConstraintLayout
    private val layoutStack : Stack<View> = Stack()
    private lateinit var bottom_sheet_layout_watermark_2 : View
    private lateinit var bottom_sheet_layout_watermark_3 : View
    private lateinit var btnColor : ImageView
    private lateinit var btnFont: ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)
        val inflatedLayout = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_filter, null)
        val btnCancel : ImageView = inflatedLayout.findViewById(R.id.btnCancel)
        hostLayout = findViewById<ConstraintLayout>(R.id.hostLayout)
        bottom_sheet_layout_watermark_2 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_2, null)
        bottom_sheet_layout_watermark_3 = layoutInflater.inflate(R.layout.bottom_sheet_layout_watermark_3, null)
        btnColor = bottom_sheet_layout_watermark_2.findViewById(R.id.btnColor)
        btnFont = bottom_sheet_layout_watermark_3.findViewById(R.id.btnFont)

        val btnColorFilter : ImageView = findViewById(R.id.btnColorFilter)
        val btnAdjust : ImageView = findViewById(R.id.btnAdjust)
        val btnHighlight : ImageView = findViewById(R.id.btnHighlight)
        val btnPicture : ImageView = findViewById(R.id.btnPicture)
        val btnSignature : ImageView = findViewById(R.id.btnSignature)
        val btnWatermark : ImageView = findViewById(R.id.btnWatermark)
        val btnText : ImageView = findViewById(R.id.btnText)
        val btnOverlay : ImageView = findViewById(R.id.btnOverlay)
        val btnColorEffect : ImageView = findViewById(R.id.btnColorEffect)

        btnColorFilter.setOnClickListener {
            val view : View = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_filter, null)
            replaceLayout(inflatedLayout)
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
    }

    private fun replaceLayout(newLayout: View) {
        hostLayout.removeAllViews()
        hostLayout.addView(newLayout)
        layoutStack.push(newLayout)

    }
}