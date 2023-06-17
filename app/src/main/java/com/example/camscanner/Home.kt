package com.example.camscanner

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.ImageView
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog


class Home : AppCompatActivity() {
    private lateinit var homeScreenBottomSheet : View
    private lateinit var settingsLayout : View
    private lateinit var homeLayout : View
    private lateinit var hostContainer : ScrollView
    private lateinit var btnPhoto : ImageView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        hostContainer = findViewById(R.id.hostContainer)
        homeLayout = layoutInflater.inflate(R.layout.home_layout, null)
        replaceLayout(homeLayout)
        homeScreenBottomSheet = layoutInflater.inflate(R.layout.home_screen_bottom_sheet, null)
        val btnBottomSheetBook : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBook)
        val btnBottomSheetDocument : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetDocument)
        val btnBottomSheetAcademicCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetAcademicCard)
        val btnBottomSheetIDCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetIDCard)
        val btnBottomSheetBusinessCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBusinessCard)
        settingsLayout = layoutInflater.inflate(R.layout.settings_layout, null)
        val addFolder: ImageView = homeLayout.findViewById(R.id.addFolder)
        val btnBusinessCard : ImageView = homeLayout.findViewById(R.id.btnBusinessCard)
        val btnIDCard : ImageView = homeLayout.findViewById(R.id.btnIDCard)
        val btnAcademicCard : ImageView = homeLayout.findViewById(R.id.btnAcademicCard)
        val btnBook : ImageView = homeLayout.findViewById(R.id.btnBook)
        val btnHome : ImageView = findViewById(R.id.btnHome)
        val btnSettings: ImageView = findViewById(R.id.btnSettings)
        val btnCaptureIcon : ImageView = findViewById(R.id.btnCaptureIcon)
        val btnDocument : ImageView = homeLayout.findViewById(R.id.btnDocument)
        val btnPhotos : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnPhotos)
        btnPhoto = homeLayout.findViewById(R.id.btnPhoto)
        btnPhoto.setOnClickListener {
            val intent = Intent(this,OnCaptureClick::class.java)
            startActivity(intent)
        }
        btnBottomSheetBook.setOnClickListener {
            openCamera()
        }
        btnBottomSheetDocument.setOnClickListener {
            openCamera()
        }
        btnBottomSheetAcademicCard.setOnClickListener {
            openCamera()
        }
        btnBottomSheetIDCard.setOnClickListener {
            openCamera()
        }
        btnBottomSheetBusinessCard.setOnClickListener {
            openCamera()
        }
        addFolder.setOnClickListener {
            openAddFolderDialog()
        }
        btnBusinessCard.setOnClickListener {
            openCamera()
        }
        btnIDCard.setOnClickListener {
            openCamera()
        }
        btnAcademicCard.setOnClickListener {
            openCamera()
        }
        btnBook.setOnClickListener {
            openCamera()
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
            openCamera()
        }
        btnPhotos.setOnClickListener {
            val intent = Intent(this, OnCaptureClick::class.java)
            startActivity(intent)
        }
    }

    private fun openCamera() {
        val intent = Intent(this, OnCaptureClick::class.java)
        startActivity(intent)
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
        if (homeScreenBottomSheet.parent != null) {
            (homeScreenBottomSheet.parent as ViewGroup).removeView(homeScreenBottomSheet)
        }
        dialog.setContentView(homeScreenBottomSheet)
        dialog.setCancelable(true)
        dialog.show()
    }
}