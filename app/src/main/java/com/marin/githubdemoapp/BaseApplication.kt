package com.marin.githubdemoapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import com.marin.githubdemoapp.di.*

class BaseApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        // Start Koin
        startKoin{
            androidLogger()
            androidContext(this@BaseApplication)
            modules(listOf(networkModule))
        }
    }
}