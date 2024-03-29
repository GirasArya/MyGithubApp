package com.dicoding.mygithubapp.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.db.remote.data.response.UserResponse
import com.dicoding.mygithubapp.repository.FavoriteUserRepository

class FavoriteViewModel(application: Application) : ViewModel() {
    private val mFavoriteUserRepository : FavoriteUserRepository = FavoriteUserRepository(application)
    fun getFavoriteUser() : LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getFavoriteUser()
}