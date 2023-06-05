package com.kirillmesh.coroutineflow.crypto_app

import androidx.lifecycle.*
import kotlinx.coroutines.flow.*

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    val state: LiveData<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State}
        .onStart { State.Loading }
        .asLiveData()
}