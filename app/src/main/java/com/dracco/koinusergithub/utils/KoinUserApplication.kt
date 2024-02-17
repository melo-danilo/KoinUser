package com.dracco.koinusergithub.utils

import android.app.Application
import com.dracco.koinusergithub.di.listModules
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class KoinUserApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin{
            androidContext(this@KoinUserApplication)
            modules(listModules)
        }
    }

}