package com.example.skucise.LayoutActivities.BasicActivities

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.LinearLayout
import com.example.skucise.R

class JarvisLoader(context: Context) : Dialog(context){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.jarvis_loader)
        window?.setLayout(
            LinearLayout.LayoutParams.MATCH_PARENT,
            LinearLayout.LayoutParams.MATCH_PARENT
            )
        window?.setBackgroundDrawableResource(R.color.white_transparent)
    }
}