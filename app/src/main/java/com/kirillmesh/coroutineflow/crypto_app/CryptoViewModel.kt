package com.kirillmesh.coroutineflow.crypto_app

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    init {
        viewModelScope.launch {
            repository.getCurrencyList()
        }
    }

    val state: Flow<State> = repository.refreshCurrencyList
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { State.Loading }

    fun refreshList() {
        viewModelScope.launch {
            repository.getCurrencyList()
        }
    }
}