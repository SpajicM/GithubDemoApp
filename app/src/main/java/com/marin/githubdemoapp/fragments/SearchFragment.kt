package com.marin.githubdemoapp.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.SpinnerAdapter
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
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
    private lateinit var spinnerAdapter: SpinnerAdapter

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
        spinnerAdapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_dropdown_item,
            viewModel.sortingOptions.map { it.text }
        )


        binding.spSort.adapter = spinnerAdapter

        binding.spSort.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                viewModel.setSortingOption(position)
            }

        }
            binding.rvSearch.layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.VERTICAL, false
            )

                    searchListAdapter = SearchListAdapter(object: SearchListAdapter.OnSearchItemClick {
                override fun onItemClick(item: Repo) {
                    val bundle = bundleOf(OWNER to item.owner.login, REPO_NAME to item.name)
                    findNavController().navigate(R.id.action_searchFragment_to_repoFragment, bundle)
                }

                override fun onAuthorClick(item: Repo) {
                    val bundle = bundleOf(USER_ID to item.owner.id)
                    findNavController().navigate(R.id.action_searchFragment_to_authorFragment, bundle)
                }

            })

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

    companion object {
        const val USER_ID = "USER_ID"
        const val OWNER = "OWNER"
        const val REPO_NAME = "REPO_NAME"
    }
}