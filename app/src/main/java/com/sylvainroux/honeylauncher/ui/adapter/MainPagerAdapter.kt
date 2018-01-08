package com.sylvainroux.honeylauncher.ui.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.util.Log
import com.sylvainroux.honeylauncher.ui.fragment.AppFragment
import com.sylvainroux.honeylauncher.ui.fragment.MainFragment

/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class MainPagerAdapter (fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {
    enum class PAGE_NAME (val pos: Int) {
        MAIN_PAGE (0),
        APP_PAGE (1)
    }

    override fun getItem(position: Int): Fragment {
        return when (position) {
            PAGE_NAME.MAIN_PAGE.pos -> MainFragment()
            PAGE_NAME.APP_PAGE.pos -> AppFragment()
            else ->  {
                Log.e(javaClass.canonicalName, "Unknown page")
                Fragment()
            }
        }
    }

    override fun getCount(): Int {
        return 2
    }

}