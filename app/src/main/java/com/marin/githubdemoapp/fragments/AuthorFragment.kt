package com.marin.githubdemoapp.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.marin.githubdemoapp.R
import com.marin.githubdemoapp.databinding.FragmentAuthorBinding
import com.marin.githubdemoapp.utils.Result
import com.marin.githubdemoapp.viewmodels.AuthorViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class AuthorFragment : BaseFragment(R.layout.fragment_author) {

    lateinit var binding: FragmentAuthorBinding
    private val viewModel: AuthorViewModel by sharedViewModel()

    private var userId: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAuthorBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        userId = requireArguments().getInt(SearchFragment.USER_ID)

        return binding.root
    }

    override fun setupUI() {
        viewModel.getUser(userId)
        binding.tvUrl.paint?.isUnderlineText = true
    }

    override fun setupClickHandlers() {
        binding.tvUrl.setOnClickListener {
            viewModel.user.value.let {
                val browserIntent = Intent(Intent.ACTION_VIEW, viewModel.getUrl())
                startActivity(browserIntent)
            }
        }
    }

    override fun setupObservers() {
        viewModel.userResponse.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> viewModel.user.postValue(it.data)
                    is Result.Error -> showError(it)
                }
            }
        }
    }
}