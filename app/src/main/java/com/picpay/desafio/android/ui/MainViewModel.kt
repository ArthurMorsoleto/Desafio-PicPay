package com.picpay.desafio.android.ui

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.picpay.desafio.android.data.usecases.GetContactsUseCase
import com.picpay.desafio.android.data.usecases.UpdateLocalContactsUseCase
import com.picpay.desafio.android.ui.MainActivityViewState.*
import kotlinx.coroutines.launch

class MainViewModel(
    private val getContactsUseCase: GetContactsUseCase,
    private val updateLocalContactsUseCase: UpdateLocalContactsUseCase
) : ViewModel() {

    private val _viewStateLiveData = MutableLiveData<MainActivityViewState>()

    val viewState: MutableLiveData<MainActivityViewState> get() = _viewStateLiveData

    fun onViewReady() {
        viewModelScope.launch {
            _viewStateLiveData.postValue(Loading)
            runCatching {
                getContactsUseCase()
            }.onSuccess {
                updateLocalContactsUseCase.invoke(it)
                _viewStateLiveData.postValue(ShowUsers(it))
            }.onFailure {
                _viewStateLiveData.postValue(Error(it))
            }
        }
    }
}