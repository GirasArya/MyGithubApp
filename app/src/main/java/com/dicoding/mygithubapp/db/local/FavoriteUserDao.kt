package com.dicoding.mygithubapp.db.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavoriteUserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addFavoriteUser(user: FavoriteUser)

    @Delete
    fun deleteFavoriteUser(user: FavoriteUser)

    @Query("SELECT * FROM FavoriteUser ORDER BY userID")
    fun getFavoriteUser(): LiveData<List<FavoriteUser>>
}