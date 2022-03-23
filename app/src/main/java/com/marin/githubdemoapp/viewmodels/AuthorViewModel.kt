package com.marin.githubdemoapp.viewmodels

import android.net.Uri
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.marin.githubdemoapp.entities.api.User
import com.marin.githubdemoapp.network.repositories.GithubRepository
import com.marin.githubdemoapp.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorViewModel(private val repository: GithubRepository) : ViewModel() {
    val userResponse = MutableLiveData<Result<User>>()
    val user = MutableLiveData<User>()

    fun getUser(userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val response = repository.getUser(userId)
            userResponse.postValue(response)
        }
    }

    fun getUrl(): Uri? {
        return Uri.parse(user.value?.html_url)
    }
}