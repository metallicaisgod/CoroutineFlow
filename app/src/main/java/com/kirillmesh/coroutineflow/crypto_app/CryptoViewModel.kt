package com.kirillmesh.coroutineflow.crypto_app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    private val loadingList = MutableSharedFlow<State>()

    val state: Flow<State> = repository.currencyListFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { State.Loading }
        .mergeWith(loadingList)
    val state2: Flow<State> = repository.currencyListFlow
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart { State.Loading }
        .mergeWith(loadingList)


    private fun <T> Flow<T>.mergeWith(vararg flows: Flow<T>): Flow<T> {
        return merge(this, flows.asIterable().merge())
    }

    fun refreshList() {
        viewModelScope.launch {
            repository.refreshList()
            loadingList.emit(State.Loading)
        }
    }
}