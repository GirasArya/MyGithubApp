package com.dicoding.mygithubapp.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dicoding.mygithubapp.ui.MainActivity
import com.dicoding.mygithubapp.db.remote.data.response.ApiConfig
import com.dicoding.mygithubapp.db.remote.data.response.GithubResponse
import com.dicoding.mygithubapp.db.remote.data.response.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _userList = MutableLiveData<List<UserResponse>>()
    val userList: LiveData<List<UserResponse>> = _userList

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "MainViewModel"
        var ID = MainActivity.ID
    }

    //Activity Main Activity
    //render pertama dari ID statis untuk tampilan awal
    private fun displayUser() {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(ID)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userList.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })
    }

    // dari input search field di-pass ke findUser(q)
    fun findUser(q: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getSearchUser(q)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _userList.value = responseBody.items
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }
        })

    }

    init {
        displayUser()
    }
}
