package com.example.camscanner

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.ImageView
import android.widget.ScrollView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.get
import com.google.android.material.bottomsheet.BottomSheetDialog

class Home : AppCompatActivity() {
    private lateinit var homeScreenBottomSheet : View
    private lateinit var settingsLayout : View
    private lateinit var homeLayout : View
    private lateinit var hostContainer : ScrollView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        homeLayout = layoutInflater.inflate(R.layout.home_layout, null)
        replaceLayout(homeLayout)
        homeScreenBottomSheet = layoutInflater.inflate(R.layout.home_screen_bottom_sheet, null)
        settingsLayout = layoutInflater.inflate(R.layout.settings_layout, null)
        val addFolder: ImageView = homeLayout.findViewById(R.id.addFolder)
        val btnHome : ImageView = findViewById(R.id.btnHome)
        val btnSettings: ImageView = findViewById(R.id.btnSettings)
        val btnCaptureIcon : ImageView = findViewById(R.id.btnCaptureIcon)
        val btnDocument : ImageView = homeLayout.findViewById(R.id.btnDocument)
        val btnPhotos : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnPhotos)
        hostContainer = findViewById(R.id.hostContainer)
        addFolder.setOnClickListener {
            openAddFolderDialog()
        }
        btnCaptureIcon.setOnClickListener {
            openBottomSheet()
        }
        btnHome.setOnClickListener {
            btnSettings.setImageResource(R.drawable.settings_icon)
            btnHome.setImageResource(R.drawable.home_selected_icon)
            replaceLayout(homeLayout)
        }
        btnSettings.setOnClickListener {
            btnSettings.setImageResource(R.drawable.settings_seleted_icon)
            btnHome.setImageResource(R.drawable.home_icon)
            replaceLayout(settingsLayout)
        }
        btnDocument.setOnClickListener {
            val intent = Intent(this, EditDocument::class.java)
            startActivity(intent)
        }
        btnPhotos.setOnClickListener {
            val intent = Intent(this, OnCaptureClick::class.java)
            startActivity(intent)
        }
    }

    private fun openAddFolderDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val cancelButton : ImageView =   dialog.findViewById<ImageView>(R.id.cancelButton)
        dialog.setCancelable(true)
        dialog.show()
        cancelButton.setOnClickListener {
            dialog.dismiss()
        }
    }

    private fun replaceLayout(newLayout: View) {
        hostContainer.removeAllViews()
        hostContainer.addView(newLayout)
    }
    private fun openBottomSheet() {

        val dialog = BottomSheetDialog(this)
        dialog.setContentView(homeScreenBottomSheet)
        dialog.setCancelable(true)
        dialog.show()
    }
}