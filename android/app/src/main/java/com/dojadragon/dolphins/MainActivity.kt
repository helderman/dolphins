package com.dojadragon.dolphins

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(intentActivateWallpaperWithFallback)
        finish()

        /***
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnActivate).setOnClickListener {
            startActivity(intentActivateWallpaperWithFallback)
            finish()
        }

        findViewById<Button>(R.id.btnList).setOnClickListener {
            startActivity(intentListWallpapers)
            finish()
        }

        findViewById<Button>(R.id.btnLater).setOnClickListener {
            finish()
        }
         ***/
    }

    private val intentActivateWallpaperWithFallback: Intent
        get() =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
                intentActivateWallpaper
            else
                intentListWallpapers

    private val intentActivateWallpaper: Intent
        @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
        get() =
            Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).also {
                it.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    ComponentName(this, DolphinsService::class.java))
            }

    private val intentListWallpapers: Intent
        get() =
            Intent(WallpaperManager.ACTION_LIVE_WALLPAPER_CHOOSER)
}
