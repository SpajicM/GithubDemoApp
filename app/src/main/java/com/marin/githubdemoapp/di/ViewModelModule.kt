package com.marin.githubdemoapp.di

import com.marin.githubdemoapp.viewmodels.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
}