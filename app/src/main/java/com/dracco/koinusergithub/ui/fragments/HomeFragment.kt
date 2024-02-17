package com.dracco.koinusergithub.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.dracco.koinusergithub.api.model.response.User
import com.dracco.koinusergithub.databinding.FragmentHomeBinding
import com.dracco.koinusergithub.extensions.showSnackBar
import com.dracco.koinusergithub.extensions.showSnackBarRed
import com.dracco.koinusergithub.ui.adapter.UserListAdapter
import com.dracco.koinusergithub.viewModels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<UserViewModel>()

    private lateinit var adapter: UserListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupObserver()
        initList()
    }

    private fun initList() {

        adapter = UserListAdapter(::userOnClick)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter =  this@HomeFragment.adapter
        }
    }

    private fun userOnClick(user: User) {
        binding.root.showSnackBar(user.login)
    }

    private fun setupObserver() {

        viewModel.getUsers()

        viewModel.userList.observe(viewLifecycleOwner) { response ->
            adapter.addAll(response)
        }

        viewModel.error.observe(viewLifecycleOwner) { response ->
            binding.root.showSnackBarRed(response)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

}