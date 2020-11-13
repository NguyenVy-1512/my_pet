package com.nhat.boiterplate.ui.shop

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

@Suppress("DEPRECATION")
class TabPagerAdapter(fm: FragmentManager, lifecycle: Lifecycle, private var numberOfTabs: Int) : FragmentStateAdapter(fm, lifecycle) {

    override fun createFragment(position: Int): Fragment {
        when (position) {
            0 -> {
                // # Infomation Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Infomation Fragment")
                val InfomationFragment = InfomationShopTab()
                InfomationFragment.arguments = bundle
                return InfomationFragment
            }
            1 -> {
                // # Review Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Review Fragment")
                val ReviewFragment = ReviewShopTab()
                ReviewFragment.arguments = bundle
                return ReviewFragment
            }
            2 -> {
                // # Image Fragment
                val bundle = Bundle()
                bundle.putString("fragmentName", "Image Fragment")
                val ImageFragment = ImageShopTab()
                ImageFragment.arguments = bundle
                return ImageFragment
            }
            else -> return InfomationShopTab()
        }
    }

    override fun getItemCount(): Int {
        return numberOfTabs
    }
}