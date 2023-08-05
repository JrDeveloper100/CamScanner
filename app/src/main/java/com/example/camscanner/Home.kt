package com.example.camscanner

import android.Manifest
import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ScrollView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class Home : AppCompatActivity() {
    private lateinit var homeScreenBottomSheet : View
    private lateinit var settingsLayout : View
    private lateinit var homeLayout : View
    private lateinit var hostContainer : ScrollView
    private lateinit var btnPhotos : LinearLayout
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
    private lateinit var btnBottomSheetBook : LinearLayout
    private lateinit var btnBottomSheetDocument : LinearLayout
    private lateinit var btnBottomSheetAcademicCard : LinearLayout
    private lateinit var btnBottomSheetIDCard : LinearLayout
    private lateinit var btnBottomSheetBusinessCard : LinearLayout
    private lateinit var linearLayout1 : LinearLayout
    private lateinit var linearLayout2 : LinearLayout
    private lateinit var linearLayout3 : LinearLayout
    private lateinit var linearLayout4 : LinearLayout
    private lateinit var linearLayout5 : LinearLayout
    private lateinit var linearLayout6 : LinearLayout
    private lateinit var cancelButton : ImageView
    private lateinit var customDialog : View
    private lateinit var buttonCancel : LinearLayout
    private lateinit var btnNotNow : Button
    private lateinit var btnNotThisTime : Button
    private lateinit var rateUsDialog : View
    private lateinit var feedbackDialog : View
    private var isDarkModeEnabled = false
    private lateinit var feedbackIcon : ImageView
    private lateinit var removeAdsIcons : ImageView
    private lateinit var rateUsIcon : ImageView
    private lateinit var privacyPolicyIcon : ImageView
    private lateinit var shareIcon : ImageView
    private lateinit var darkModeIcon : ImageView
    private lateinit var imageView13 : LinearLayout
    private lateinit var imageView12 : ImageView
    private lateinit var imageView15 : LinearLayout
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        hostContainer = findViewById(R.id.hostContainer)
        btnHomeContainer = findViewById(R.id.btnHomeContainer)
        btnSettingsContainer = findViewById(R.id.btnSettingsContainer)
        homeLayout = layoutInflater.inflate(R.layout.home_layout, null)
        replaceLayout(homeLayout)
        homeScreenBottomSheet = layoutInflater.inflate(R.layout.home_screen_bottom_sheet, null)
        btnBottomSheetBook = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBook)
        btnBottomSheetDocument = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetDocument)
        btnBottomSheetAcademicCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetAcademicCard)
        btnBottomSheetIDCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetIDCard)
        btnBottomSheetBusinessCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBusinessCard)
        settingsLayout = layoutInflater.inflate(R.layout.settings_layout, null)
        linearLayout1 = settingsLayout.findViewById(R.id.linearLayout1)
        linearLayout2 = settingsLayout.findViewById(R.id.linearLayout2)
        linearLayout3 = settingsLayout.findViewById(R.id.linearLayout3)
        linearLayout4 = settingsLayout.findViewById(R.id.linearLayout4)
        linearLayout5 = settingsLayout.findViewById(R.id.linearLayout5)
        linearLayout6 = settingsLayout.findViewById(R.id.linearLayout6)
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
        customDialog = layoutInflater.inflate(R.layout.custom_dialog,null)
        rateUsDialog = layoutInflater.inflate(R.layout.custom_dialog_rate_us,null)
        feedbackDialog = layoutInflater.inflate(R.layout.custom_dialog_feedback,null)
        cancelButton = customDialog.findViewById(R.id.cancelButton)
        buttonCancel = customDialog.findViewById(R.id.buttonCancel)
        btnNotNow = rateUsDialog.findViewById(R.id.btnNotNow)
        btnNotThisTime = feedbackDialog.findViewById(R.id.btnNotThisTime)
        feedbackIcon = settingsLayout.findViewById(R.id.feedbackIcon)
        removeAdsIcons = settingsLayout.findViewById(R.id.removeAdsIcons)
        rateUsIcon = settingsLayout.findViewById(R.id.rateUsIcon)
        privacyPolicyIcon = settingsLayout.findViewById(R.id.privacyPolicyIcon)
        shareIcon = settingsLayout.findViewById(R.id.shareIcon)
        darkModeIcon = settingsLayout.findViewById(R.id.darkModeIcon)
        imageView13 = homeLayout.findViewById(R.id.imageView13)
        imageView12 = homeLayout.findViewById(R.id.imageView12)
        imageView15 = homeLayout.findViewById(R.id.imageView15)
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
            linearLayout2.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
            linearLayout3.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
            linearLayout4.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
            linearLayout5.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
            linearLayout6.setBackgroundResource(R.drawable.rounded_settings_icon_design_dark_mode)
            btnBottomSheetBusinessCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetIDCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetAcademicCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetDocument.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetBook.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnPhotos.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            feedbackIcon.setImageResource(R.drawable.feedback_icon_inner_dark_mode)
            removeAdsIcons.setImageResource(R.drawable.remove_ads_icon_inner_dark_mode)
            rateUsIcon.setImageResource(R.drawable.rate_us_icon_inner_dark_mode)
            privacyPolicyIcon.setImageResource(R.drawable.privacy_policy_icon_dark_mode)
            shareIcon.setImageResource(R.drawable.share_icon_inner_dark_mode)
            darkModeIcon.setImageResource(R.drawable.dark_mode_icon_inner_dark_mode)
        }else{
            btnBusinessCard.setBackgroundResource(R.drawable.business_card_rounded_design_light_mode)
            btnIDCard.setBackgroundResource(R.drawable.id_card_rounded_design_light_mode)
            btnAcademicCard.setBackgroundResource(R.drawable.academic_card_rounded_design_light_mode)
            btnDocument.setBackgroundResource(R.drawable.document_rounded_design_light_mode)
            btnBook.setBackgroundResource(R.drawable.book_rounded_design_light_mode)
            btnPhoto.setBackgroundResource(R.drawable.photos_rounded_design_light_mode)
            linearLayout1.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            linearLayout2.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            linearLayout3.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            linearLayout4.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            linearLayout5.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            linearLayout6.setBackgroundResource(R.drawable.rounded_settings_icon_design_light_mode)
            btnBottomSheetBusinessCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetIDCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetAcademicCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetDocument.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetBook.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnPhotos.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            linearLayout1.setBackgroundResource(R.drawable.settings_screen_icon_design_light_mode)
        }

        btnPhoto.setOnClickListener {
            openCamera("Photo")
        }
        btnBottomSheetBook.setOnClickListener {
            openCamera("Book")
        }
        btnBottomSheetDocument.setOnClickListener {
            openCamera("Document")
        }
        btnBottomSheetAcademicCard.setOnClickListener {
            openCamera("AcademicCard")
        }
        btnBottomSheetIDCard.setOnClickListener {
            openCamera("IDCard")
        }
        btnBottomSheetBusinessCard.setOnClickListener {
            val intent = Intent(this, BusinessCard::class.java)
            startActivity(intent)
        }
        addFolder.setOnClickListener {
            openAddFolderDialog()
        }
        btnBusinessCard.setOnClickListener {
            val intent = Intent(this, BusinessCard::class.java)
            startActivity(intent)
        }
        btnIDCard.setOnClickListener {
            openCamera("IDCard")
        }
        btnAcademicCard.setOnClickListener {
            openCamera("AcademicCard")
        }
        btnBook.setOnClickListener {
            openCamera("Book")
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
            openCamera("Document")
        }
        btnPhotos.setOnClickListener {
            openCamera("Photo")
        }
        btnRateUs.setOnClickListener {
            openRateUsDialog()
        }
        btnFeedback.setOnClickListener {
            openFeedbackDialog()
        }
        imageView13.setOnClickListener {
            if (allPermissionsGranted()){
                startScanning()
            }else{
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
        imageView12.setOnClickListener {
            if (allPermissionsGranted()){
                startScanning()
            }else{
                ActivityCompat.requestPermissions(
                    this,
                    REQUIRED_PERMISSIONS,
                    REQUEST_CODE_PERMISSIONS
                )
            }
        }
        imageView15.setOnClickListener {
            val intent = Intent(this, QRGenerate::class.java)
            startActivity(intent)
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

    private fun startScanning() {
        val integrator = IntentIntegrator(this)
        integrator.setOrientationLocked(false) // Allow both portrait and landscape
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result: IntentResult? =
            IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null && result.contents != null) {
            // Show result in a dialog
            showQrResultDialog(result.contents)
        }
    }

    private fun showQrResultDialog(qrResult: String) {
        val dialogView = layoutInflater.inflate(R.layout.dialog_qr_result, null)
        val textQrResult = dialogView.findViewById<TextView>(R.id.textQrResult)
        val btnDismiss = dialogView.findViewById<Button>(R.id.btnDismiss)

        textQrResult.text = qrResult

        val alertDialog = AlertDialog.Builder(this)
            .setView(dialogView)
            .setCancelable(false)
            .create()

        btnDismiss.setOnClickListener {
            alertDialog.dismiss()
        }

        alertDialog.show()
    }

    private fun openFeedbackDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_feedback)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnNotThisTime : Button =   dialog.findViewById<Button>(R.id.btnNotThisTime)
        dialog.setCancelable(true)
        dialog.show()

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            btnNotThisTime.setBackgroundResource(R.drawable.button_design_3_dark_mode)
        }else{
            btnNotThisTime.setBackgroundResource(R.drawable.button_design_3_light_mode)
        }

    }

    private fun openRateUsDialog() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog_rate_us)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val btnNotNow : Button =   dialog.findViewById<Button>(R.id.btnNotNow)
        val starIcon : ImageView =   dialog.findViewById<ImageView>(R.id.starIcon)
        dialog.setCancelable(true)
        dialog.show()

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            btnNotNow.setBackgroundResource(R.drawable.button_design_3_dark_mode)
            starIcon.setImageResource(R.drawable.rate_us_icon_bar_dark_mode)
        }else{
            btnNotNow.setBackgroundResource(R.drawable.button_design_3_light_mode)
        }

    }


    private fun openCamera(cameraType: String) {
        val intent = Intent(this, OnCaptureClick::class.java)
        intent.putExtra("cameraType",cameraType)
        startActivity(intent)
    }

    private fun openAddFolderDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.custom_dialog)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        val buttonCancel : LinearLayout =   dialog.findViewById<LinearLayout>(R.id.buttonCancel)
        val editTextText2 : EditText =   dialog.findViewById<EditText>(R.id.editTextText2)
        dialog.setCancelable(true)
        dialog.show()
        buttonCancel.setOnClickListener {
            dialog.dismiss()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            buttonCancel.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            editTextText2.setHintTextColor(resources.getColor(R.color.white))
        }else{
            buttonCancel.setBackgroundResource(R.drawable.cancel_icon_design_light_mode)
        }

    }

    private fun replaceLayout(newLayout: View) {
        hostContainer.removeAllViews()
        hostContainer.addView(newLayout)
    }

   private fun allPermissionsGranted() : Boolean{
       for (permission in REQUIRED_PERMISSIONS) {
           if (ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED) {
               return false
           }
       }
       return true
    }

    companion object {
        private const val TAG = "MainActivity"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
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