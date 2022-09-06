package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.api.repository.ApiRepository
import com.picpay.desafio.android.data.local.repository.LocalRepository
import com.picpay.desafio.android.data.model.User

class GetContactsUseCaseImpl(
    private val apiRepository: ApiRepository,
    private val localRepository: LocalRepository
) : GetContactsUseCase {

    override suspend fun invoke(): List<User> {
        val localUsers = localRepository.getUsers()
        return if (localUsers.isEmpty()) {
            apiRepository.getUsers()
        } else {
            localUsers
        }.sortedBy { it.name }
    }
}