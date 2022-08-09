package com.picpay.desafio.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.usecases.GetContactsUseCase
import com.picpay.desafio.android.data.usecases.GetLocalContactsUseCase
import com.picpay.desafio.android.data.usecases.UpdateLocalContactsUseCase
import com.picpay.desafio.android.ui.MainActivityViewState.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val getLocalContactsUseCase: GetLocalContactsUseCase,
    private val updateLocalContactsUseCase: UpdateLocalContactsUseCase
) : ViewModel() {

    private val _viewStateLiveData = MutableLiveData<MainActivityViewState>()

    val viewState: MutableLiveData<MainActivityViewState> get() = _viewStateLiveData

    fun getStoredUsers() {
        viewModelScope.launch {
            _viewStateLiveData.postValue(Loading)
            runCatching {
                getLocalContactsUseCase()
            }.onSuccess { localResponse ->
                if (localResponse.isNotEmpty()) {
                    _viewStateLiveData.postValue(ShowUsers(localResponse.sortedBy { it.name }))
                } else {
                    getUsers()
                }
            }.onFailure {
                getUsers()
            }
        }
    }

    private fun getUsers() {
        viewModelScope.launch {
            runCatching {
                getContactsUseCase()
            }.onSuccess { response ->
                updateLocalContactsUseCase(response)
                _viewStateLiveData.postValue(ShowUsers(response))
            }.onFailure { error ->
                _viewStateLiveData.postValue(Error(error))
            }
        }
    }
}