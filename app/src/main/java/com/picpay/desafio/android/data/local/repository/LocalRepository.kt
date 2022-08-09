package com.picpay.desafio.android.data.local.repository

import com.picpay.desafio.android.data.model.User

interface LocalRepository {

    fun getUsers(): List<User>

    fun addUsers(response: List<User>)
}