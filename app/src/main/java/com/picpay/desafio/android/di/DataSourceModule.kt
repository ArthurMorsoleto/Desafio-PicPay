package com.picpay.desafio.android.di

import com.picpay.desafio.android.data.local.datasource.ContactsDataSource
import com.picpay.desafio.android.data.local.datasource.ContactsDataSourceImpl
import org.koin.dsl.module

object DataSourceModule {

    fun provide() = module {
        single<ContactsDataSource> { ContactsDataSourceImpl(get(), get()) }
    }
}