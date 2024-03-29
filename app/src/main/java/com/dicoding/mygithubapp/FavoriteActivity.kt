package com.dicoding.mygithubapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapp.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubapp.db.remote.data.response.UserResponse
import com.dicoding.mygithubapp.helper.ViewModelFactory
import com.dicoding.mygithubapp.ui.adapter.UserAdapter
import com.dicoding.mygithubapp.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            title = "Your Favorite User"
        }

        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.green)))

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setItemOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserResponse) {
                // Handle item click here
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                    it.putExtra(DetailActivity.EXTRA_ID, user.id)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, user.avatarUrl)
                    it.putExtra(DetailActivity.EXTRA_URL, user.url)
                    startActivity(it)
                }
            }
        })
        binding.rvFavoriteList.layoutManager = LinearLayoutManager(this)
        binding.rvFavoriteList.setHasFixedSize(true)
        binding.rvFavoriteList.adapter = adapter

        favoriteViewModel.getFavoriteUser().observe(this) {
            if (it != null) {
                adapter.setFavoriteUser(it)
            }
        }
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }
}