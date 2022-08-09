package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.local.repository.LocalRepository
import com.picpay.desafio.android.data.model.User

class GetLocalContactsUseCaseImpl(
    private val localRepository: LocalRepository
) : GetLocalContactsUseCase {

    override suspend fun invoke(): List<User> {
        return localRepository.getUsers()
    }
}