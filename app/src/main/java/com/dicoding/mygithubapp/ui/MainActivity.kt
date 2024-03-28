package com.dicoding.mygithubapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapp.databinding.ActivityMainBinding
import com.dicoding.mygithubapp.db.remote.data.response.UserResponse
import com.dicoding.mygithubapp.ui.adapter.UserAdapter
import com.dicoding.mygithubapp.ui.viewmodel.MainViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val TAG = "MainActivity"
        var ID = "Ras"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = "Github Search"
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)
        mainViewModel.userList.observe(this) { user ->
            setUserData(user)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView, actionId, event ->
                    mainViewModel.findUser(searchView.text.toString())
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    false
                }
        }

        val layoutManager = LinearLayoutManager(this)
        binding.rvUserlist.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUserlist.addItemDecoration(itemDecoration)


    }

    private fun setUserData(userData: List<UserResponse>) {
        val adapter = UserAdapter()
        adapter.notifyDataSetChanged()
        adapter.setItemOnClickCallback(object : UserAdapter.OnItemClickCallback {
            override fun onItemClicked(user: UserResponse) {
                Intent(this@MainActivity, DetailActivity::class.java).also {
                    it.putExtra(DetailActivity.EXTRA_USERNAME, user.login)
                    startActivity(it)
                }
            }
        })
        adapter.submitList(userData)
        binding.rvUserlist.adapter = adapter
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}