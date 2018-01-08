package com.sylvainroux.honeylauncher.data.model

import android.content.Context
import android.content.pm.ApplicationInfo
import android.graphics.drawable.Drawable
import java.io.File


/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class AppModel(context: Context, val info: ApplicationInfo) {
    private var label: String? = null
    private var icon: Drawable? = null

    private var isMounted: Boolean = false
    private var apkFile: File = File(info.sourceDir)

    init {
        loadLabel(context)
    }

    fun getAppInfo(): ApplicationInfo {
        return info
    }

    fun getApplicationPackageName(): String {
        return getAppInfo().packageName
    }

    fun getLabel(): String? {
        return label
    }

    fun getIcon(context: Context): Drawable? {
        if (icon == null) {
            if (apkFile.exists()) {
                icon = getAppInfo().loadIcon(context.packageManager)
                return icon
            } else {
                isMounted = false
            }
        } else if (!isMounted) {
            // If the app wasn't mounted but is now mounted, reload
            // its icon.
            if (apkFile.exists()) {
                isMounted = true
                icon = getAppInfo().loadIcon(context.packageManager)
                return icon
            }
        } else {
            return icon
        }

        return context.resources.getDrawable(android.R.drawable.sym_def_app_icon)
    }

    fun loadLabel(context: Context) {
        if (label == null || !isMounted) {
            if (!apkFile.exists()) {
                isMounted = false
                label = getAppInfo().packageName
            } else {
                isMounted = true
                val label = getAppInfo().loadLabel(context.packageManager)
                this.label = label?.toString() ?: getAppInfo().packageName
            }
        }
    }
}