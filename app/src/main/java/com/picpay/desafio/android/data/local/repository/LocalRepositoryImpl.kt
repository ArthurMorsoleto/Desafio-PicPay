package com.picpay.desafio.android.data.local.repository

import com.picpay.desafio.android.data.local.datasource.ContactsDataSource
import com.picpay.desafio.android.data.model.ContactsBO
import com.picpay.desafio.android.data.model.User

class LocalRepositoryImpl(
    private val dataSource: ContactsDataSource
) : LocalRepository {

    override fun getUsers(): List<User> {
        val localData = dataSource.get()
        return localData.users
    }

    override fun addUsers(response: List<User>) {
        dataSource.add(ContactsBO(response))
    }
}