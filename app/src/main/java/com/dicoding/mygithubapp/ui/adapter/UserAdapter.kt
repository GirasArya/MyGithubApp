package com.dicoding.mygithubapp.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dicoding.mygithubapp.databinding.UserItemBinding
import com.dicoding.mygithubapp.db.local.FavoriteUser
import com.dicoding.mygithubapp.db.remote.data.response.UserResponse
import com.dicoding.mygithubapp.helper.UserCallback

//Adapter Recycler view untuk List
class UserAdapter : ListAdapter<UserResponse, UserAdapter.UserViewHolder>(DIFF_CALLBACK) {
    private val listFavUser = ArrayList<FavoriteUser>()
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<UserResponse>() {
            override fun areItemsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: UserResponse, newItem: UserResponse): Boolean {
                return oldItem == newItem
            }
        }
    }

    fun setFavoriteUser(listUser: List<FavoriteUser>) {
        val diffCallback = UserCallback(this.listFavUser, listUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listUser)
        diffResult.dispatchUpdatesTo(this)
    }

    private var onItemClickCallback: OnItemClickCallback? = null

    fun setItemOnClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    inner class UserViewHolder(val binding: UserItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: UserResponse) {
            binding.root.setOnClickListener {
                onItemClickCallback?.onItemClicked(user)
            }
            Glide.with(binding.rvUserImage.context)
                .load(user.avatarUrl)
                .into(binding.rvUserImage)
            binding.tvUsername.text = user.login
            binding.tvURL.text = user.url
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = UserItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val userPosition = getItem(position)
        holder.bind(userPosition)
    }

    interface OnItemClickCallback {
        fun onItemClicked(user: UserResponse)
    }
}