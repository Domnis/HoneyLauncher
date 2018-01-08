package com.sylvainroux.honeylauncher.ui.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.WindowManager
import com.sylvainroux.honeylauncher.data.manager.UserManager
import com.sylvainroux.honeylauncher.databinding.MainActivityBinding
import com.sylvainroux.honeylauncher.ui.adapter.MainPagerAdapter

/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class MainActivity : AppCompatActivity() {

    lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window?.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS) //it required to have the status bar transparent

        binding = MainActivityBinding.inflate(LayoutInflater.from(this))

        binding.wallpaper.setImageDrawable(UserManager.getInstance(this).getWallpaper())

        initPager()

        setContentView(binding.root)
    }

    override fun onBackPressed() {
        if (binding.pager.currentItem != 0) {
            binding.pager.currentItem = 0
            return
        }

        super.onBackPressed()
    }

    private fun initPager() {
        binding.pager.adapter = MainPagerAdapter(supportFragmentManager)
    }
}