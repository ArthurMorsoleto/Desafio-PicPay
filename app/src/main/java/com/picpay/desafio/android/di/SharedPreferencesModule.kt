package com.picpay.desafio.android.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module

object SharedPreferencesModule {

    private const val NAME = "MySharedPreferences"

    fun provide() = module {
        single { getSharedPreferences(androidApplication()) }
    }

    private fun getSharedPreferences(app: Application): SharedPreferences {
        return app.applicationContext.getSharedPreferences(
            NAME,
            Context.MODE_PRIVATE
        )
    }
}