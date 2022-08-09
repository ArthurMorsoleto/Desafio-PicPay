package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.api.service.PicPayService
import org.koin.dsl.module
import retrofit2.Retrofit

object ServiceModule {

    fun provide() = module {
        single { get<Retrofit>().create(PicPayService::class.java) }
    }
}