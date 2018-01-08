package com.sylvainroux.honeylauncher.data.manager

import android.app.WallpaperManager
import android.content.Context
import android.graphics.drawable.Drawable



/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class UserManager private constructor (val context: Context) {

    init {
        //nothing to do yet
    }

    companion object : SingletonHolder<UserManager, Context>(::UserManager)

    fun getWallpaper() : Drawable {
        val wallpaperManager = WallpaperManager.getInstance(context)
        return wallpaperManager.drawable
    }

}