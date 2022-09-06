package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.usecases.*
import org.koin.dsl.module

object UseCaseModule {

    fun provide() = module {
        single<GetContactsUseCase> { GetContactsUseCaseImpl(get(), get()) }
        single<UpdateLocalContactsUseCase> { UpdateLocalContactsUseCaseImpl(get()) }
        single<GetLocalContactsUseCase> { GetLocalContactsUseCaseImpl(get()) }
    }
}