package com.marin.githubdemoapp.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.githubdemoapp.entities.api.ListResponse
import com.marin.githubdemoapp.entities.api.Repo
import com.marin.githubdemoapp.network.repositories.GithubRepository
import com.marin.githubdemoapp.utils.Result
import com.marin.githubdemoapp.views.SpinnerItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SearchViewModel(private val repository: GithubRepository): ViewModel() {

    val searchQueryTextEdit = MutableLiveData("")
    val repositories = MutableLiveData<Result<ListResponse<Repo>>>()
    val sortingOptions = listOf(
        SpinnerItem("Forks", "forks"),
        SpinnerItem("Stars", "stars"),
        SpinnerItem("Updated", "updated"))
    private val selectedSortingOption = MutableLiveData(sortingOptions.first())

    fun getRepos() {
        viewModelScope.launch(Dispatchers.IO) {
            if (!searchQueryTextEdit.value.isNullOrBlank()) {
                val response = repository.getRepositories(searchQueryTextEdit.value!!, selectedSortingOption.value!!.value)
                repositories.postValue(response)
            }
        }
    }

    fun setSortingOption(position: Int) {
        selectedSortingOption.value = sortingOptions[position]
        getRepos()
    }
}