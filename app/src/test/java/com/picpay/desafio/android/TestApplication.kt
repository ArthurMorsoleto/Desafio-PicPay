package com.picpay.desafio.android

import android.app.Application
import com.squareup.picasso.Picasso
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.AppTheme)
        Picasso.setSingletonInstance(Picasso.Builder(this).build())
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
            modules(listOf())
        }
    }
}
