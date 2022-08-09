package com.picpay.desafio.android.data.local.datasource

import com.picpay.desafio.android.data.model.ContactsBO

interface ContactsDataSource {

    fun add(contacts: ContactsBO)

    fun get(): ContactsBO

    fun clear()
}