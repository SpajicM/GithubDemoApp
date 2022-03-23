package com.marin.githubdemoapp.viewmodels


import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.githubdemoapp.entities.api.Repo
import com.marin.githubdemoapp.network.repositories.GithubRepository
import com.marin.githubdemoapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RepoViewModel(private val repository: GithubRepository) : ViewModel() {
    val repoResponse = MutableLiveData<Result<Repo>>()
    val repo = MutableLiveData<Repo>()

    fun getRepo(owner: String, repoName: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getRepoDetails(owner, repoName)
            repoResponse.postValue(response)
        }
    }

    fun getUrl(): Uri? {
        return Uri.parse(repo.value?.html_url)
    }
}