package com.dicoding.mygithubapp.db.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize
@Entity
@Parcelize

data class FavoriteUser (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userID")
    var userID : Int = 0,

    @ColumnInfo(name = "username")
    var userName : String? = null,

    @ColumnInfo(name = "avatar")
    var avatar : String? = null,

    @ColumnInfo(name = "userURL")
    var userURL : String? = null
) : Parcelable