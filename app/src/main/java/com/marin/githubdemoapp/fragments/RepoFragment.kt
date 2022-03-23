package com.marin.githubdemoapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marin.githubdemoapp.R
import com.marin.githubdemoapp.databinding.FragmentRepoBinding
import com.marin.githubdemoapp.utils.Result
import com.marin.githubdemoapp.viewmodels.RepoViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class RepoFragment : BaseFragment(R.layout.fragment_author) {

    lateinit var binding: FragmentRepoBinding
    private val viewModel: RepoViewModel by sharedViewModel()

    private lateinit var owner: String
    private lateinit var repoName: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRepoBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        owner = requireArguments().getString(SearchFragment.OWNER, "")
        repoName = requireArguments().getString(SearchFragment.REPO_NAME, "")

        return binding.root
    }

    override fun setupUI() {
        viewModel.getRepo(owner, repoName)
        binding.tvUrl.paint?.isUnderlineText = true
    }

    override fun setupClickHandlers() {
        binding.tvUrl.setOnClickListener {
            viewModel.repo.value.let {
                val browserIntent = Intent(Intent.ACTION_VIEW, viewModel.getUrl())
                startActivity(browserIntent)
            }
        }
    }

    override fun setupObservers() {
        viewModel.repoResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> viewModel.repo.postValue(it.data)
                    is Result.Error -> showError(it)
                }
            }
        }
    }
}