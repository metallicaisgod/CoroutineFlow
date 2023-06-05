package com.kirillmesh.coroutineflow.crypto_app

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlin.random.Random

object CryptoRepository {

    private val currencyNames = listOf("BTC", "ETH", "USDT", "BNB", "USDC")
    private val currencyList = mutableListOf<Currency>()

    private val _refreshCurrencyList = MutableSharedFlow<List<Currency>>()
    val refreshCurrencyList = _refreshCurrencyList.asSharedFlow()

    suspend fun getCurrencyList() {
        delay(3000)
        generateCurrencyList()
        _refreshCurrencyList.emit(currencyList.toList())
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
