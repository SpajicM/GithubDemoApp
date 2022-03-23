package com.marin.githubdemoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.marin.githubdemoapp.R
import com.marin.githubdemoapp.adapters.SearchListAdapter
import com.marin.githubdemoapp.databinding.FragmentSearchBinding
import com.marin.githubdemoapp.entities.api.Repo
import com.marin.githubdemoapp.utils.Result
import com.marin.githubdemoapp.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel

class SearchFragment : BaseFragment(R.layout.fragment_search) {

    lateinit var binding: FragmentSearchBinding
    private val viewModel: SearchViewModel by sharedViewModel()
    private lateinit var searchListAdapter: SearchListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner

        return binding.root
    }

    override fun setupUI() {
        binding.rvSearch.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL, false
        )

        searchListAdapter = SearchListAdapter(object: SearchListAdapter.OnSearchItemClick {
            override fun onItemClick(item: Repo) {
                // TODO
            }

            override fun onAuthorClick(item: Repo) {
                // TODO
            }

        } )

        binding.rvSearch.adapter = searchListAdapter
    }

    override fun setupClickHandlers() {
        binding.button.setOnClickListener {
            viewModel.getRepos()
        }
    }

    override fun setupObservers() {
        viewModel.repositories.observe(viewLifecycleOwner) {
            if (it != null) {
                when (it) {
                    is Result.Success -> searchListAdapter.updateDataset(it.data.items)
                    is Result.Error -> showError(it)
                }
            }
        }
    }
}