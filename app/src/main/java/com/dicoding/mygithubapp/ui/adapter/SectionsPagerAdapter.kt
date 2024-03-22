package com.dicoding.mygithubapp.ui.adapter

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.dicoding.mygithubapp.FollowFragment

class SectionsPagerAdapter(
    activity: AppCompatActivity,
    supportFragmentManager: FragmentManager,
    private val username : String
) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int {
        return 2
    }

    // set adapter untuk navigation tab_layout
    override fun createFragment(position: Int): Fragment {
        val fragment = FollowFragment()
        fragment.arguments = Bundle().apply {
            putInt(FollowFragment.ARG_POSITION, position + 1)
            putString(FollowFragment.ARG_USERNAME, username)
        }
        return fragment
    }
}