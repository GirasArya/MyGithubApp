package com.dicoding.mygithubapp.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.R
import com.dicoding.mygithubapp.databinding.ActivityDetailBinding
import com.dicoding.mygithubapp.ui.adapter.SectionsPagerAdapter
import com.dicoding.mygithubapp.ui.viewmodel.DetailUserViewModel
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.elevation = 0f

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            title = "Github Profile"
        }

        val username = intent.getStringExtra(EXTRA_USERNAME) ?: ""

        binding.viewPagerDetailUser.adapter =
            SectionsPagerAdapter(this, supportFragmentManager, username)


        TabLayoutMediator(
            binding.tabLayoutDetailUser,
            binding.viewPagerDetailUser
        ) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        val bundle = Bundle()
        bundle.putString(EXTRA_USERNAME, username)

        val viewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailUserViewModel::class.java)

        viewModel.isLoading.observe(this) {
            showLoadingDetail(it)
        }

        if (username != null) {
            viewModel.displayUserDetail(username)
        }
        viewModel.getUserDetail().observe(this) {
            if (it != null) {
                binding.apply {
                    tvName.text = it.name
                    tvUsername.text = it.login
                    tvFollower.text = "${it.followers} Followers"
                    tvFollowing.text = "${it.following} Following"
                    Glide.with(this@DetailActivity)
                        .load(it.avatarUrl)
                        .into(ivUserImage)
                }
            }
        }
    }

    private fun showLoadingDetail(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarUserDetail.visibility = View.VISIBLE
        } else {
            binding.progressBarUserDetail.visibility = View.GONE
        }
    }
}