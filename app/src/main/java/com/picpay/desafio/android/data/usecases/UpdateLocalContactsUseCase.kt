package com.picpay.desafio.android.data.usecases

import com.picpay.desafio.android.data.model.User

interface UpdateLocalContactsUseCase {

    operator fun invoke(contacts: List<User>)
}