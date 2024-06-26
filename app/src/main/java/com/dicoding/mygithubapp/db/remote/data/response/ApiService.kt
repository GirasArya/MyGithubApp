package com.dicoding.mygithubapp.db.remote.data.response


import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getSearchUser(
        @Query("q") q: String
    ): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailUserResponse>

    @GET("users/{username}/followers")
    fun getFollowers(@Path("username") username: String): Call<List<UserResponse>>

    @GET("users/{username}/following")
    fun getFollowing(@Path("username") username: String): Call<List<UserResponse>>
}