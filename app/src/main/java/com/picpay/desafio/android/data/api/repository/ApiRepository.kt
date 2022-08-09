package com.picpay.desafio.android.data.api.repository

import com.picpay.desafio.android.data.model.User

interface ApiRepository {

    suspend fun getUsers(): List<User>
}