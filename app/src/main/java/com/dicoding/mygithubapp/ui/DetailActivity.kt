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

class DetailActivity : AppCompatActivity(){

    private lateinit var binding: ActivityDetailBinding
    var isCheck = false

    companion object {
        const val EXTRA_USERNAME = "extra_username"
        const val EXTRA_ID = "extra_id"

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
        val id = intent.getIntExtra(EXTRA_ID, 0)

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

        val viewModel = ViewModelProvider(this).get(DetailUserViewModel::class.java)

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

        binding.fabAddFave.setOnClickListener{
            isCheck = !isCheck
            if (isCheck){
                viewModel.addToFavoriteUser(username, id)
                binding.fabAddFave.setImageResource(R.drawable.ic_bookmark_fill)
            }
            else{
                viewModel.deleteFavoriteUser(username, id)
                binding.fabAddFave.setImageResource(R.drawable.ic_bookmark_outline)
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