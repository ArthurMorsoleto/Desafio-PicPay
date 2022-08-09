package com.picpay.desafio.android.data.local.datasource

import android.content.SharedPreferences
import com.google.gson.Gson
import com.picpay.desafio.android.data.model.ContactsBO

class ContactsDataSourceImpl(
    private val gson: Gson,
    private val prefs: SharedPreferences
) : ContactsDataSource {

    override fun add(contacts: ContactsBO) {
        val data = gson.toJson(contacts)
        with(prefs.edit()) {
            putString(KEY, data)
            commit()
        }
    }

    override fun get(): ContactsBO {
        val storedData = prefs.getString(KEY, "")
        return if (storedData.isNullOrEmpty()) {
            ContactsBO(users = emptyList())
        } else {
            gson.fromJson(storedData, ContactsBO::class.java)
        }
    }

    override fun clear() {
        with(prefs.edit()) {
            remove(KEY)
            commit()
        }
    }

    companion object {
        private const val KEY = "ContactsDataSourceKey"
    }
}