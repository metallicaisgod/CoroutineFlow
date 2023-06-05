package com.kirillmesh.coroutineflow.crypto_app

sealed class State {
    object Initial : State()
    object Loading : State()
    data class Content(val currencyList: List<Currency>) : State()
}