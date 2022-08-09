package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.local.repository.LocalRepository
import com.picpay.desafio.android.data.model.User

class UpdateLocalContactsUseCaseImpl(
    private val localRepository: LocalRepository
) : UpdateLocalContactsUseCase {

    override fun invoke(contacts: List<User>) {
        localRepository.addUsers(contacts)
    }
}