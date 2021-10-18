package com.dojadragon.dolphins

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    //private val drawer = DolphinsDrawer(this, SystemClock::elapsedRealtime)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        /*
        startActivity(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) intentActivate() else intentList())
        finish()

         */

        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnActivate).setOnClickListener {
            startActivity(if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) intentActivate() else intentList())
            finish()
        }

        findViewById<Button>(R.id.btnList).setOnClickListener {
            startActivity(intentList())
            finish()
        }

        findViewById<Button>(R.id.btnLater).setOnClickListener {
            finish()
        }
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun intentActivate(): Intent {
        val intent = Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER)
        intent.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
            ComponentName(this, DolphinsService::class.java))
        return intent
    }

    private fun intentList(): Intent {
        return Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
    }
}
