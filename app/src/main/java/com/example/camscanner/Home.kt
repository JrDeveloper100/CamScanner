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
import androidx.navigation.findNavController
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.zxing.integration.android.IntentIntegrator
import com.google.zxing.integration.android.IntentResult


class Home : AppCompatActivity() {
    private lateinit var homeScreenBottomSheet : View
    private lateinit var btnBottomSheetPhoto : LinearLayout
    private lateinit var btnHomeContainer : ConstraintLayout
    private lateinit var btnSettingsContainer : ConstraintLayout
    private lateinit var btnBottomSheetBook : LinearLayout
    private lateinit var btnBottomSheetDocument : LinearLayout
    private lateinit var btnBottomSheetAcademicCard : LinearLayout
    private lateinit var btnBottomSheetIDCard : LinearLayout
    private lateinit var btnBottomSheetBusinessCard : LinearLayout
    lateinit var btnSettings : ImageView
    lateinit var btnHome : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        btnHomeContainer = findViewById(R.id.btnHomeContainer)
        btnSettingsContainer = findViewById(R.id.btnSettingsContainer)
        homeScreenBottomSheet = layoutInflater.inflate(R.layout.home_screen_bottom_sheet, null)
        btnBottomSheetBook = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBook)
        btnBottomSheetDocument = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetDocument)
        btnBottomSheetAcademicCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetAcademicCard)
        btnBottomSheetIDCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetIDCard)
        btnBottomSheetBusinessCard = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetBusinessCard)
        btnHome = findViewById(R.id.btnHome)
        btnSettings = findViewById(R.id.btnSettings)
        val btnCaptureIcon : ImageView = findViewById(R.id.btnCaptureIcon)
        btnBottomSheetPhoto = homeScreenBottomSheet.findViewById(R.id.btnBottomSheetPhoto)
        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        Constant.themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            btnBottomSheetBusinessCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetIDCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetAcademicCard.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetDocument.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetBook.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnBottomSheetPhoto.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
        }else{
            btnBottomSheetBusinessCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetIDCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetAcademicCard.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetDocument.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetBook.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnBottomSheetPhoto.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
        }

        btnBottomSheetPhoto.setOnClickListener {
            Constant.conversionType = "Photo"
            openCamera("Photo")
        }
        btnBottomSheetBook.setOnClickListener {
            Constant.conversionType = "Book"
            openCamera("Book")
        }
        btnBottomSheetDocument.setOnClickListener {
            Constant.conversionType = "Document"
            openCamera("Document")
        }
        btnBottomSheetAcademicCard.setOnClickListener {
            Constant.conversionType = "AcademicCard"
            openCamera("AcademicCard")
        }
        btnBottomSheetIDCard.setOnClickListener {
            Constant.conversionType = "IDCard"
            openCamera("IDCard")
        }
        btnBottomSheetBusinessCard.setOnClickListener {
//            val intent = Intent(this, BusinessCard::class.java)
//            startActivity(intent)
            Constant.conversionType = "BusinessCard"
            openCamera("BusinessCard")
        }

        btnCaptureIcon.setOnClickListener {
            openBottomSheet()
        }


        btnHomeContainer.setOnClickListener {
            btnSettings.setImageResource(R.drawable.settings_icon)
            btnHome.setImageResource(R.drawable.home_selected_icon)
            val navController = findNavController(R.id.homeFragmentContainer)
            if (navController.currentDestination?.id != R.id.homeFragment){
                navController.navigate(R.id.action_settingsFragment_to_homeFragment)
            }
        }
        btnSettingsContainer.setOnClickListener {
            btnSettings.setImageResource(R.drawable.settings_seleted_icon)
            btnHome.setImageResource(R.drawable.home_icon)
            val navController = findNavController(R.id.homeFragmentContainer)
            if (navController.currentDestination?.id != R.id.settingsFragment){
                navController.navigate(R.id.action_homeFragment_to_settingsFragment)
            }
        }







    }

    fun reAdjustButtons(){
        btnSettings.setImageResource(R.drawable.settings_seleted_icon)
        btnHome.setImageResource(R.drawable.home_icon)
    }

    private fun openCamera(cameraType: String) {
        Constant.imageBasket.clear()
        Constant.imagesPath.clear()
        Constant.selectedFilter = null
        Constant.firstTime = true
        val intent = Intent(this, OnCaptureClick::class.java)
        intent.putExtra("cameraType",cameraType)
        startActivity(intent)
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