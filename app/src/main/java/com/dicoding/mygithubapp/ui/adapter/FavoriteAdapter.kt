package com.dicoding.mygithubapp.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.databinding.UserItemBinding
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.helper.UserCallback

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    private val listFavUser = ArrayList<FavoriteUser>()
    fun setFavoriteUser(listUser: List<FavoriteUser>) {
        val diffCallback = UserCallback(this.listFavUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val binding = UserItemBinding.inflate(
            LayoutInflater.from(parent.context), parent,
            false
        )
        return FavoriteViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return listFavUser.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }

    inner class FavoriteViewHolder(private val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favUser: FavoriteUser) {
            with(binding) {
                Glide.with(binding.rvUserImage.context)
                    .load(favUser.avatar)
                    .into(binding.rvUserImage)
                tvUsername.text = favUser.userName
                tvURL.text = favUser.userURL
            }
        }
    }
}
