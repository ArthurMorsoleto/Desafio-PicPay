package com.picpay.desafio.android.data.api.repository

import com.picpay.desafio.android.data.model.User
import com.picpay.desafio.android.data.api.service.PicPayService
import com.picpay.desafio.android.di.CoroutinesModule
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.withContext

class ApiRepositoryImpl(
    private val service: PicPayService,
    private val coroutineScope: CoroutinesModule.DefaultCoroutineScope
) : ApiRepository {

    override suspend fun getUsers(): List<User> {
        return withContext(coroutineScope.coroutineContext) {
            service.getUsers()
        }
    }
}