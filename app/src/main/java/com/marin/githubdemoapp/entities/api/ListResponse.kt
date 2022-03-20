package com.marin.githubdemoapp.entities.api

data class ListResponse<T> (
    val total_count : Int,
    val incomplete_results : Boolean,
    val items : List<T>
)