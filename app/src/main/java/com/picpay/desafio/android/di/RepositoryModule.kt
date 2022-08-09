package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.api.repository.ApiRepository
import com.picpay.desafio.android.data.api.repository.ApiRepositoryImpl
import com.picpay.desafio.android.data.local.repository.LocalRepository
import com.picpay.desafio.android.data.local.repository.LocalRepositoryImpl
import org.koin.dsl.module

object RepositoryModule {

    fun provide() = module {
        factory<ApiRepository> { ApiRepositoryImpl(get(), get()) }
        factory<LocalRepository> { LocalRepositoryImpl(get()) }
    }
}