package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.api.repository.ApiRepository
import com.picpay.desafio.android.data.model.User

class GetContactsUseCaseImpl(
    private val apiRepository: ApiRepository
) : GetContactsUseCase {

    override suspend fun invoke(): List<User> {
        return apiRepository.getUsers()
    }
}