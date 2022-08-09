package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.model.User

interface GetLocalContactsUseCase {

    suspend operator fun invoke(): List<User>
}