package com.dicoding.mygithubapp.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.db.local.FavoriteUserDao
import com.dicoding.mygithubapp.db.local.FavoriteUserDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteUserRepository(application: Application) {
    private val mUserDao : FavoriteUserDao
    private val executorService : ExecutorService = Executors.newSingleThreadExecutor()
    init{
        val db = FavoriteUserDatabase.getDatabase(application)
        mUserDao = db.favoriteUserDao()
    }
    fun getFavoriteUser(): LiveData<List<FavoriteUser>> = mUserDao.getFavoriteUser()
    fun addFavoriteUser(favUser: FavoriteUser) {
        executorService.execute { mUserDao.addFavoriteUser(favUser) }
    }

    fun deleteFavoriteUser(favUser: FavoriteUser) {
        executorService.execute { mUserDao.deleteFavoriteUser(favUser) }
    }
}