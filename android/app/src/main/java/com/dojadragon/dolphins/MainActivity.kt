package com.dojadragon.dolphins

import android.app.WallpaperManager
import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(intentActivateWallpaper)
        finish()
    }

    private val intentActivateWallpaper: Intent
        get() =
            Intent(WallpaperManager.ACTION_CHANGE_LIVE_WALLPAPER).also {
                it.putExtra(WallpaperManager.EXTRA_LIVE_WALLPAPER_COMPONENT,
                    ComponentName(this, DolphinsWallpaperService::class.java))
            }
}
