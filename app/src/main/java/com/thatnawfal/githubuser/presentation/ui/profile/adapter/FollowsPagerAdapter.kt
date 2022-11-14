package com.thatnawfal.githubuser.presentation.ui.profile.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.thatnawfal.githubuser.presentation.ui.profile.FollowsFragment

class FollowsPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment){
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        val fragments = FollowsFragment()

        fragments.arguments = Bundle().apply {
            putInt(FollowsFragment.SECTION_TABS, position + 1)
        }

        return fragments
    }
}