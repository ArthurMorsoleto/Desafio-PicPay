package com.picpay.desafio.android.di

import com.picpay.desafio.android.ui.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelModule {

    fun provide() = module {
        viewModel { MainViewModel(get(), get()) }
    }
}