package com.dicoding.mygithubapp.ui.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.db.local.FavoriteUserDao
import com.dicoding.mygithubapp.db.local.FavoriteUserDatabase
import com.dicoding.mygithubapp.db.remote.data.response.ApiConfig
import com.dicoding.mygithubapp.db.remote.data.response.DetailUserResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application : Application) : AndroidViewModel(application) {
    private val userDetail = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private lateinit var userDao : FavoriteUserDao
    private lateinit var userDb : FavoriteUserDatabase

    init {
        userDb = FavoriteUserDatabase.getDatabase(application)
        userDao = userDb.favoriteUserDao()
    }
    companion object {
        private const val DETAILTAG = "DetailViewModel"
    }

    fun displayUserDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailUserResponse> {
            override fun onResponse(
                call: Call<DetailUserResponse>,
                response: Response<DetailUserResponse>
            ) {
                _isLoading.value = false
               if (response.isSuccessful){
                   userDetail.postValue(response.body())
               }
            }
            override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(DETAILTAG, "onFailure: ${t.message}")
            }
        })
    }

    fun getUserDetail() : LiveData<DetailUserResponse> {
        return userDetail
    }

    fun addToFavoriteUser(username: String, id : Int, avatarUrl : String, userUrl : String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(id, username, avatarUrl, userUrl)
            userDao.addFavoriteUser(user)
        }
    }

    fun deleteFavoriteUser(username: String, id: Int, avatarUrl : String, userUrl : String){
        CoroutineScope(Dispatchers.IO).launch {
            var user = FavoriteUser(id, username, avatarUrl, userUrl)
            userDao.deleteFavoriteUser(user)
        }
    }
}