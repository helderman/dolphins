package com.dojadragon.dolphins

import android.content.Context
import android.graphics.Canvas
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //setContentView(SampleView(this, DolphinsDrawer(this)))
    }

    /*
    inner class SampleView(context: Context?, private val drawer: DolphinsDrawer) : View(context) {
        override fun onDraw(canvas: Canvas?) {
            //super.onDraw(canvas)
            invalidate()
        }
    }
    */
}