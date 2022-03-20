package com.marin.githubdemoapp.di

import com.marin.githubdemoapp.utils.Constants
import com.marin.githubdemoapp.network.interfaces.IWebService
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


val networkModule = module {
    factory { provideRetrofit(provideOkHttpClient()) }
    single { provideNetworkApi(get()) }
}

fun provideOkHttpClient(): OkHttpClient {
    return OkHttpClient().newBuilder()
        .build()
}

fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
    return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()

}

fun provideNetworkApi(retrofit: Retrofit): IWebService =
        retrofit.create(IWebService::class.java)