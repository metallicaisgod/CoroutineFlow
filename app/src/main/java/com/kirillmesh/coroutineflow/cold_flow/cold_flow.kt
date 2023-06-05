package com.kirillmesh.coroutineflow.cold_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val flow = getFlow()
    coroutineScope.launch {
        flow.onCompletion{
            println(it)
            //Пока нет подписки flow не выполняется
        }
    }
    coroutineScope.launch {
        println(flow.first())
        //если пописчикам больше не нужны данные, flow прекращает работу
    }
    coroutineScope.launch {
        flow.collect{
            println(it)
            //на каждую подписку создается новый поток данных
        }
    }
}

fun getFlow() = flow {
    repeat(100){
        println("Emitted: $it")
        emit(it)
        delay(1000)
    }
}