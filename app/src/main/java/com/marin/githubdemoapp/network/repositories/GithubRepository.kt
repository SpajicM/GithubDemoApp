package com.marin.githubdemoapp.network.repositories

import com.marin.githubdemoapp.network.interfaces.IWebService

class GithubRepository(private val service: IWebService) : BaseRepository() {
    suspend fun getRepositories(searchQuery: String) =
        getResult { service.getRepositories(searchQuery) }

    suspend fun getUser(userId: Int) =
        getResult { service.getUser(userId) }

    suspend fun getRepoDetails(owner: String, repoName: String) =
        getResult { service.getRepo(owner, repoName) }
}