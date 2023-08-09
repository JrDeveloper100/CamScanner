package com.example.camscanner

import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.appbar.MaterialToolbar

class Preview : AppCompatActivity() {
    private lateinit var btnClosePreview : Button
    private lateinit var toolbar: MaterialToolbar
    private lateinit var recyclerView : RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        btnClosePreview = findViewById(R.id.btnClosePreview)
        recyclerView = findViewById(R.id.recyclerView)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        val previewAdapter = PreviewAdapter(Constant.imageBasket)
        recyclerView.adapter = previewAdapter
        btnClosePreview.setOnClickListener {
            finish()
        }

        val themeMode = resources.configuration.uiMode and android.content.res.Configuration.UI_MODE_NIGHT_MASK
        if(themeMode == android.content.res.Configuration.UI_MODE_NIGHT_YES){
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_dark_mode)

        }else{
            toolbar.setNavigationIcon(R.drawable.arrow_left_icon_light_mode)
        }

    }
}