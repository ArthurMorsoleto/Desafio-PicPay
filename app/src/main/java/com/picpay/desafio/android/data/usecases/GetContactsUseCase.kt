package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.model.User

interface GetContactsUseCase {

    suspend operator fun invoke(): List<User>
}