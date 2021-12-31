package com.avinash.mykmmchat.android

import android.app.Application
import com.avinash.mykmmchat.android.di.appModule
import com.avinash.mykmmchat.di.initKoin
import org.koin.android.ext.koin.androidContext

class MyApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        initKoin {
            androidContext(applicationContext)
            modules(appModule())
        }

    }
}