package com.picpay.desafio.android.di

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import org.koin.core.scope.Scope
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitModule {

    private const val baseUrl = "https://609a908e0f5a13001721b74e.mockapi.io/picpay/api/"

    fun provide() = module {
        single { GsonBuilder().create() }
        single { buildHttpClient() }
        single { buildRetrofit() }
    }

    private fun Scope.buildRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create(get()))
            .client(get())
            .build()
    }

    private fun Scope.buildHttpClient(): OkHttpClient {
        return OkHttpClient.Builder().build()
    }
}