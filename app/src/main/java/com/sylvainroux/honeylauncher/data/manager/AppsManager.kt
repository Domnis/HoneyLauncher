package com.sylvainroux.honeylauncher.data.manager

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.support.v4.content.AsyncTaskLoader
import com.sylvainroux.honeylauncher.data.manager.AppsManager.AppsLoader.Companion.ALPHA_COMPARATOR
import com.sylvainroux.honeylauncher.data.model.AppModel
import java.text.Collator
import java.util.*


/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class AppsManager private constructor (val context: Context) {

    init {
        //nothing to do yet
    }

    companion object : SingletonHolder<AppsManager, Context>(::AppsManager)

    fun getApps() {
    }

    inner class AppsLoader(context: Context) : AsyncTaskLoader<ArrayList<AppModel>>(context) {
        var mInstalledApps: ArrayList<AppModel>? = null

        private val packageManager: PackageManager = context.packageManager

        init {
        }

        override fun loadInBackground(): ArrayList<AppModel> {
            // retrieve the list of installed applications
            var apps: List<ApplicationInfo>? = packageManager.getInstalledApplications(0)

            if (apps == null) {
                apps = ArrayList()
            }

            // create corresponding apps and load their labels
            val items = ArrayList<AppModel>(apps.size)
            for (i in apps.indices) {
                val pkg = apps[i].packageName

                // only apps which are launchable
                if (packageManager.getLaunchIntentForPackage(pkg) != null) {
                    val app = AppModel(context, apps[i])
                    items.add(app)
                }
            }

            // sort the list
            Collections.sort(items, ALPHA_COMPARATOR)
            return items
        }

        override fun deliverResult(apps: ArrayList<AppModel>?) {
            if (isReset) {
                // An async query came in while the loader is stopped.  We
                // don't need the result.
                if (apps != null) {
                    onReleaseResources(apps)
                }
            }

            mInstalledApps = apps

            if (isStarted) {
                // If the Loader is currently started, we can immediately
                // deliver its results.
                super.deliverResult(apps)
            }

            // At this point we can release the resources associated with
            // 'oldApps' if needed; now that the new result is delivered we
            // know that it is no longer in use.
            if (apps != null) {
                onReleaseResources(apps)
            }
        }

        override fun onStartLoading() {
            if (mInstalledApps != null) {
                // If we currently have a result available, deliver it
                // immediately.
                deliverResult(mInstalledApps)
            }

            if (takeContentChanged() || mInstalledApps == null) {
                // If the data has changed since the last time it was loaded
                // or is not currently available, start a load.
                forceLoad()
            }
        }

        override fun onStopLoading() {
            // Attempt to cancel the current load task if possible.
            cancelLoad()
        }

        override fun onCanceled(apps: ArrayList<AppModel>) {
            super.onCanceled(apps)

            // At this point we can release the resources associated with 'apps'
            // if needed.
            onReleaseResources(apps)
        }

        override protected fun onReset() {
            // Ensure the loader is stopped
            onStopLoading()

            // At this point we can release the resources associated with 'apps'
            // if needed.
            if (mInstalledApps != null) {
                onReleaseResources(mInstalledApps!!)
                mInstalledApps = null
            }
        }

        /**
         * Helper method to do the cleanup work if needed, for example if we're
         * using Cursor, then we should be closing it here
         *
         * @param apps
         */
        protected fun onReleaseResources(apps: ArrayList<AppModel>) {
            // do nothing
        }

        companion object {

            /**
             * Perform alphabetical comparison of application entry objects.
             */
            val ALPHA_COMPARATOR: Comparator<AppModel> = object : Comparator<AppModel> {
                private val sCollator = Collator.getInstance()
                override fun compare(object1: AppModel, object2: AppModel): Int {
                    return sCollator.compare(object1.getLabel(), object2.getLabel())
                }
            }
        }
    }
}