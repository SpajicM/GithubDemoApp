package com.marin.githubdemoapp.network.repositories

import com.marin.githubdemoapp.network.interfaces.IWebService

class GithubRepository(private val service: IWebService) : BaseRepository() {
    suspend fun getRepositories(searchQuery: String) =
        getResult { service.getRepositories(searchQuery) }
}