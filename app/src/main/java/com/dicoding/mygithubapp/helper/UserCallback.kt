package com.dicoding.mygithubapp.helper

import androidx.recyclerview.widget.DiffUtil
import com.dicoding.mygithubapp.db.local.FavoriteUser

class UserCallback(private val oldUserList : List<FavoriteUser>, private val newUserList : List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldUserList.size
    override fun getNewListSize(): Int = newUserList.size

    override fun areItemsTheSame(oldUserPosition: Int, newUserPosition: Int): Boolean {
        return oldUserList[newUserPosition].userID == newUserList[newUserPosition].userID
    }

    override fun areContentsTheSame(oldUserPosition: Int, newUserPosition: Int): Boolean {
        val oldUser = oldUserList[oldUserPosition]
        val newUser = newUserList[newUserPosition]
        return oldUser.userID == newUser.userID && oldUser.userName == newUser.userName && oldUser.userURL == newUser.userURL && oldUser.avatar == newUser.avatar
    }


}