package com.picpay.desafio.android

import android.app.Application
import com.picpay.desafio.android.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        initializeKoinApplication()
    }

    private fun initializeKoinApplication() {
        startKoin {
            androidContext(this@MainApplication)
            modules(
                listOf(
                    RetrofitModule.provide(),
                    ServiceModule.provide(),
                    ViewModelModule.provide(),
                    CoroutinesModule.provide(),
                    RepositoryModule.provide(),
                    SharedPreferencesModule.provide(),
                    DataSourceModule.provide(),
                    UseCaseModule.provide()
                )
            )
        }
    }
}