package com.dicoding.mygithubapp

import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapp.databinding.ActivityFavoriteBinding
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.helper.ViewModelFactory
import com.dicoding.mygithubapp.ui.adapter.FavoriteAdapter
import com.dicoding.mygithubapp.ui.viewmodel.FavoriteViewModel

class FavoriteActivity : AppCompatActivity() {

    private var _activityFavoriteBinding : ActivityFavoriteBinding? = null
    private val binding get() = _activityFavoriteBinding
    private lateinit var adapter: FavoriteAdapter



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_activityFavoriteBinding?.root)

        val actionBar: ActionBar? = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            title = "Your Favorite User"
        }
        supportActionBar?.setBackgroundDrawable(ColorDrawable(resources.getColor(R.color.green)))

        val favoriteViewModel = obtainViewModel(this@FavoriteActivity)
        favoriteViewModel.getFavoriteUser().observe(this){
            if (it != null){
                adapter.setFavoriteUser(it)
            }
        }

        adapter = FavoriteAdapter()
        binding?.rvFavoriteList?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavoriteList?.setHasFixedSize(true)
        binding?.rvFavoriteList?.adapter = adapter

        adapter.setOnItemCLickCallback(object : FavoriteAdapter.OnItemClickCallback {
            override fun onItemClicked(user: FavoriteUser, position: Int) {
                Intent(this@FavoriteActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, user.userName)
                    it.putExtra(DetailActivity.EXTRA_ID, user.userID)
                    it.putExtra(DetailActivity.EXTRA_AVATAR, user.avatar)
                    it.putExtra(DetailActivity.EXTRA_URL, user.userURL)
                    startActivity(it)
                }
            }
        })
    }


    private fun obtainViewModel(activity : AppCompatActivity) : FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityFavoriteBinding = null
    }
}