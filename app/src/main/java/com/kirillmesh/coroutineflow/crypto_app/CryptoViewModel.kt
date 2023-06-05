package com.kirillmesh.coroutineflow.crypto_app

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import kotlinx.coroutines.flow.*

class CryptoViewModel : ViewModel() {

    private val repository = CryptoRepository

    val state: LiveData<State> = repository.getCurrencyList()
        .filter { it.isNotEmpty() }
        .map { State.Content(it) as State }
        .onStart {
            Log.d("CryptoViewModel", "OnStart")
            State.Loading
        }
        .onEach {
            Log.d("CryptoViewModel", "OnEach")
        }
        .onCompletion {
            Log.d("CryptoViewModel", "onCompletion")
        }
        .asLiveData()
}