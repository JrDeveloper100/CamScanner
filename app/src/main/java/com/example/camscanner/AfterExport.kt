package com.example.camscanner

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.LinearLayout
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.bottomsheet.BottomSheetDialog

class AfterExport : AppCompatActivity() {

    private lateinit var toolbar : MaterialToolbar
    private lateinit var btnShare : LinearLayout
    private lateinit var shareBottomSheet : View
    private lateinit var passwordBottomSheet : View
    private lateinit var btnShareAsImage : LinearLayout
    private lateinit var btnShareAsPdf : LinearLayout
    private lateinit var btnSharePdfWithPassword : LinearLayout
    private lateinit var btnSharePdfWithPasswordButton : LinearLayout
    private lateinit var delete : LinearLayout
    private lateinit var mail : LinearLayout
    private lateinit var share : LinearLayout
    private lateinit var preview : LinearLayout
    private lateinit var btnPreview : LinearLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_after_export)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        btnPreview = findViewById(R.id.btnPreview)
        shareBottomSheet = layoutInflater.inflate(R.layout.share_bottom_sheet, null)
        passwordBottomSheet = layoutInflater.inflate(R.layout.password_bottom_sheet, null)
        btnShareAsImage = shareBottomSheet.findViewById(R.id.btnShareAsImage)
        btnShareAsPdf = shareBottomSheet.findViewById(R.id.btnShareAsPdf)
        btnSharePdfWithPassword = shareBottomSheet.findViewById(R.id.btnSharePdfWithPassword)
        btnSharePdfWithPasswordButton = shareBottomSheet.findViewById(R.id.btnSharePdfWithPasswordButton)
        delete = findViewById(R.id.delete)
        mail = findViewById(R.id.mail)
        share = findViewById(R.id.share)
        preview = findViewById(R.id.preview)

        btnShare = findViewById(R.id.btnShare)
        btnShare.setOnClickListener {
            openBottomSheet()
        }
        btnSharePdfWithPasswordButton.setOnClickListener {
            openPasswordBottomSheet()
        }
        btnPreview.setOnClickListener {
            val intent = Intent(this,RemoveImages::class.java)
            startActivity(intent)
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)
            btnShareAsImage.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnShareAsPdf.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            btnSharePdfWithPassword.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            delete.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            mail.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            share.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
            preview.setBackgroundResource(R.drawable.all_cards_rounded_design_dark_mode)
        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
            btnShareAsImage.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnShareAsPdf.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            btnSharePdfWithPassword.setBackgroundResource(R.drawable.home_screen_bottom_sheet_icons_design_light_mode)
            delete.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            mail.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            share.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
            preview.setBackgroundResource(R.drawable.after_export_icons_design_light_mode)
        }

    }

    private fun openPasswordBottomSheet() {
        val dialog = BottomSheetDialog(this)
        if (passwordBottomSheet.parent != null) {
            (passwordBottomSheet.parent as ViewGroup).removeView(passwordBottomSheet)
        }
        dialog.setContentView(passwordBottomSheet)
        dialog.setCancelable(true)
        dialog.show()

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES) {
            dialog.findViewById<EditText>(R.id.password)
                ?.setHintTextColor(resources.getColor(R.color.white))
            dialog.findViewById<EditText>(R.id.confirmPassword)
                ?.setHintTextColor(resources.getColor(R.color.white))
        }

    }

    private fun openBottomSheet() {
        val dialog = BottomSheetDialog(this)
        if (shareBottomSheet.parent != null) {
            (shareBottomSheet.parent as ViewGroup).removeView(shareBottomSheet)
        }
        dialog.setContentView(shareBottomSheet)
        dialog.setCancelable(true)
        dialog.show()
    }
}