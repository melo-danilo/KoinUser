package com.dracco.koinusergithub.ui.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.dracco.koinusergithub.api.model.response.Repository
import com.dracco.koinusergithub.api.model.response.UserDetail
import com.dracco.koinusergithub.databinding.FragmentUserDetailBinding
import com.dracco.koinusergithub.extensions.showSnackBar
import com.dracco.koinusergithub.ui.adapter.RepositoryAdapter
import com.dracco.koinusergithub.viewModels.UserViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class UserDetailFragment : Fragment() {

    private var _binding: FragmentUserDetailBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModel<UserViewModel>()

    private lateinit var adapterRepository: RepositoryAdapter

    private val args by navArgs<UserDetailFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentUserDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecycler()
        setupObserver()
    }

    private fun setupRecycler() {

        adapterRepository = RepositoryAdapter(::openBrowser, ::shareRepositoryLink)
        binding.recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterRepository
        }
    }

    private fun shareRepositoryLink(urlRepository: Repository) {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, urlRepository.htmlUrl)
            type = "text/plain"
        }

        val shareIntent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }


    private fun openBrowser(urlRepository: Repository) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse(urlRepository.htmlUrl)
            )
        )

    }

    private fun setupObserver() {

        viewModel.getUserByUsername(args.userName)
        viewModel.getReposByUsername(args.userName)


        viewModel.repos.observe(viewLifecycleOwner){
            it?.let {
                adapterRepository.addAll(it)
            }
        }

        viewModel.user.observe(viewLifecycleOwner){
            it?.let {
                setupUI(it)
            }
        }

        viewModel.error.observe(viewLifecycleOwner){
            binding.root.showSnackBar(it)
        }
    }

    private fun setupUI(detail: UserDetail) {

        binding.apply {

            detail.avatarUrl.let {
                image.load(it) {
                    crossfade(true)
                }
            }

            textName.text = detail.name
            textEmail.text = detail.email
            textFollowers.text = buildString {
                append("Seguidores: ")
                append(detail.followers.toString())
            }
            textFollowing.text = buildString {
                append("Seguindo: ")
                append(detail.following.toString())
            }
            textRepos.text = buildString {
                append("Repositórios: ")
                append(detail.publicRepos.toString())
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }


}