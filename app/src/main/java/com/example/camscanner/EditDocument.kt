package com.example.camscanner

import android.content.Intent
import android.media.Image
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import java.util.Stack

class EditDocument : AppCompatActivity() {
    private lateinit var hostLayout : ConstraintLayout
    private val layoutStack : Stack<View> = Stack()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_document)
        val inflatedLayout = layoutInflater.inflate(R.layout.bottom_sheet_layout_color_filter, null)
        val btnCancel : ImageView = inflatedLayout.findViewById(R.id.btnCancel)
        hostLayout = findViewById<ConstraintLayout>(R.id.hostLayout)

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
    }

    private fun replaceLayout(newLayout: View) {
        hostLayout.removeAllViews()
        hostLayout.addView(newLayout)
        layoutStack.push(newLayout)

    }
}