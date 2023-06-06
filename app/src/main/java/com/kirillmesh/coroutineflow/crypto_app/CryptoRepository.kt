package com.kirillmesh.coroutineflow.crypto_app

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlin.random.Random

object CryptoRepository {

    private val currencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val currencyList = mutableListOf<Currency>()

    private val _refreshListState = MutableSharedFlow<Unit>()

    val currencyListFlow: Flow<List<Currency>> = flow {
        delay(3000)
        generateCurrencyList()
        emit(currencyList.toList())
        _refreshListState.collect {
            delay(3000)
            generateCurrencyList()
            emit(currencyList.toList())
        }
    }.stateIn(
        scope = CoroutineScope(Dispatchers.Default),
        started = SharingStarted.Lazily,
        initialValue = currencyList.toList()
    )

    suspend fun refreshList() {
        _refreshListState.emit(Unit)
    }

    private fun generateCurrencyList() {
        val prices = buildList {
            repeat(currencyNames.size) {
                add(Random.nextInt(1000, 2000))
            }
        }
        val newData = buildList {
            for ((index, currencyName) in currencyNames.withIndex()) {
                val price = prices[index]
                val currency = Currency(name = currencyName, price = price)
                add(currency)
            }
        }
        currencyList.clear()
        currencyList.addAll(newData)
    }
}
