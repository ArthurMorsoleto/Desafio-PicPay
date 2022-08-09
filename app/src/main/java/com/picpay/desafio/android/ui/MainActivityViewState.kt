package com.picpay.desafio.android.ui

import com.picpay.desafio.android.data.model.User

open class MainActivityViewState {

    object Loading : MainActivityViewState()

    data class ShowUsers(val users: List<User>) : MainActivityViewState()

    data class Error(val cause: Throwable) : MainActivityViewState()
}