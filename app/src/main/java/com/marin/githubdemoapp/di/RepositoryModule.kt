package com.marin.githubdemoapp.di

import com.marin.githubdemoapp.network.repositories.GithubRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { GithubRepository(get())}
}