package com.sylvainroux.honeylauncher.ui.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sylvainroux.honeylauncher.databinding.AppFragmentBinding

/**
 * Created by sylvain.roux
 * on 05/01/2018.
 */
class AppFragment : Fragment() {
    lateinit var binding : AppFragmentBinding

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)

        binding = AppFragmentBinding.inflate(LayoutInflater.from(context))

        return binding.root
    }
}