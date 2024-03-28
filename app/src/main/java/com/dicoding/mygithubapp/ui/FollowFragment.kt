package com.dicoding.mygithubapp.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.mygithubapp.databinding.FragmentFollowBinding
import com.dicoding.mygithubapp.ui.adapter.UserAdapter
import com.dicoding.mygithubapp.ui.viewmodel.FollowViewModel

class FollowFragment : Fragment() {
    companion object {
        const val ARG_POSITION = "position"
        const val ARG_USERNAME = "username"
    }

    private lateinit var binding: FragmentFollowBinding
    private lateinit var viewModel: FollowViewModel
    private lateinit var adapter: UserAdapter
    private lateinit var username: String


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFollowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = UserAdapter()
        viewModel = ViewModelProvider(this).get(FollowViewModel::class.java)
        username = arguments?.getString(ARG_USERNAME) ?: ""

        var position = requireArguments().getInt(ARG_POSITION)
        if (position == 1) {
            viewModel.displayUserFollowers(username)
        } else {
            viewModel.displayUserFollowing(username)
        }

        viewModel.listFollowers.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        viewModel.listFollowing.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }

        binding.rvFollowerList.layoutManager = LinearLayoutManager(requireContext())
        binding.rvFollowerList.adapter = adapter
    }
}