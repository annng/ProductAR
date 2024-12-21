package com.pam.product

import android.app.Application
import com.pam.product.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()

        // Start Koin with the app's modules
        startKoin {
            androidContext(this@App)
            modules(appModule)
        }
    }
}