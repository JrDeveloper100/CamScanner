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
import android.widget.LinearLayout
import android.widget.ScrollView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetDialog


class Home : AppCompatActivity() {
    private lateinit var homeScreenBottomSheet : View
    private lateinit var settingsLayout : View
    private lateinit var homeLayout : View
    private lateinit var hostContainer : ScrollView
    private lateinit var btnPhotos : ConstraintLayout
    private lateinit var addFolder : ImageView
    private lateinit var btnHomeContainer : ConstraintLayout
    private lateinit var btnSettingsContainer : ConstraintLayout
    private lateinit var btnRateUs : LinearLayout
    private lateinit var btnFeedback : LinearLayout
    private lateinit var btnDarkMode : LinearLayout
    private lateinit var btnBusinessCard : LinearLayout
    private lateinit var btnIDCard : LinearLayout
    private lateinit var btnAcademicCard : LinearLayout
    private lateinit var btnDocument : LinearLayout
    private lateinit var btnBook : LinearLayout
    private lateinit var btnPhoto : LinearLayout
    private lateinit var linearLayout1 : LinearLayout
    private var isDarkModeEnabled = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        hostContainer = findViewById(R.id.hostContainer)
        btnHomeContainer = findViewById(R.id.btnHomeContainer)
        btnSettingsContainer = findViewById(R.id.btnSettingsContainer)
        homeLayout = layoutInflater.inflate(R.layout.home_layout, null)
        replaceLayout(homeLayout)
        homeScreenBottomSheet = layoutInflater.inflate(R.layout.home_screen_bottom_sheet, null)
        val btnBottomSheetBook : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBook)
        val btnBottomSheetDocument : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetDocument)
        val btnBottomSheetAcademicCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetAcademicCard)
        val btnBottomSheetIDCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetIDCard)
        val btnBottomSheetBusinessCard : ConstraintLayout = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBusinessCard)
        settingsLayout = layoutInflater.inflate(R.layout.settings_layout, null)
        linearLayout1 = settingsLayout.findViewById(R.id.linearLayout1)
        btnRateUs = settingsLayout.findViewById(R.id.btnRateUs)
        btnFeedback = settingsLayout.findViewById(R.id.btnFeedback)
        btnDarkMode = settingsLayout.findViewById(R.id.btnDarkMode)
        addFolder = homeLayout.findViewById(R.id.addFolder)
        btnBusinessCard = homeLayout.findViewById(R.id.btnBusinessCard)
        btnIDCard = homeLayout.findViewById(R.id.btnIDCard)
        btnAcademicCard = homeLayout.findViewById(R.id.btnAcademicCard)
        btnBook = homeLayout.findViewById(R.id.btnBook)
        val btnHome : ImageView = findViewById(R.id.btnHome)
        val btnSettings: ImageView = findViewById(R.id.btnSettings)
        val btnCaptureIcon : ImageView = findViewById(R.id.btnCaptureIcon)
        btnDocument = homeLayout.findViewById(R.id.btnDocument)
        btnPhotos = homeScreenBottomSheet.findViewById(R.id.btnPhotos)
        btnPhoto = homeLayout.findViewById(R.id.btnPhoto)

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            btnBusinessCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnIDCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnAcademicCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnDocument.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBook.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnPhoto.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            addFolder.setImageResource(R.drawable.add_icon_dark_mode)
            linearLayout1.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
        }else{
            btnBusinessCard.setBackgroundResource(R.drawable.business_card_rounded_design_light_mode)
            btnIDCard.setBackgroundResource(R.drawable.id_card_rounded_design_light_mode)
            btnAcademicCard.setBackgroundResource(R.drawable.academic_card_rounded_design_light_mode)
            btnDocument.setBackgroundResource(R.drawable.document_rounded_design_light_mode)
            btnBook.setBackgroundResource(R.drawable.book_rounded_design_light_mode)
            btnPhoto.setBackgroundResource(R.drawable.photos_rounded_design_light_mode)
            linearLayout1.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
        }

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
            val intent = Intent(this, BusinessCard::class.java)
            startActivity(intent)
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
        btnHomeContainer.setOnClickListener {
            btnSettings.setImageResource(R.drawable.settings_icon)
            btnHome.setImageResource(R.drawable.home_selected_icon)
            replaceLayout(homeLayout)
        }
        btnSettingsContainer.setOnClickListener {
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
        btnRateUs.setOnClickListener {
            openRateUsDialog()
        }
        btnFeedback.setOnClickListener {
            openFeedbackDialog()
        }
        btnDarkMode.setOnClickListener {
            isDarkModeEnabled = !isDarkModeEnabled

            if (isDarkModeEnabled) {
                // Enable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                // Disable dark mode
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
    }

    private fun openFeedbackDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_feedback)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()

    }

    private fun openRateUsDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_rate_us)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(true)
        dialog.show()

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