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
        try {
            Picasso.setSingletonInstance(Picasso.Builder(this).build())
        } catch (e: Exception) {
        }
        startKoin {
            androidLogger()
            androidContext(this@TestApplication)
        }
    }
}
