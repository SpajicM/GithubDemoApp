package com.marin.githubdemoapp.network.interfaces

import com.marin.githubdemoapp.entities.api.ListResponse
import com.marin.githubdemoapp.entities.api.Repo
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface IWebService {
    @GET("search/repositories")
    suspend fun getRepositories(@Query("q") searchQuery: String) : Response<ListResponse<Repo>>
}