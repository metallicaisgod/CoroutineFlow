package com.kirillmesh.coroutineflow.hot_flow

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

val coroutineScope = CoroutineScope(Dispatchers.IO)

suspend fun main() {
    val flow = MutableSharedFlow<Int>()
    //Эмитят значения независимо от наличия подписчиков
    coroutineScope.launch {
        repeat(100){
            println("Emitted: $it")
            flow.emit(it)
            delay(1000)
        }
    }
    //Если в потоке больше нет данных SharedFlow не завершится

    val job1 = coroutineScope.launch {
        println(flow.first())
        //если пописчикам больше не нужны данные, SharedFlow продолжает эмитить значения
    }
    val job2 = coroutineScope.launch {
        flow.collect{
            println(it)
            //При каждой подписке подписчики получают одни и те же элементы
        }
    }
    job1.join()
    job2.join()
}

fun getFlow() = flow {
    repeat(100){
        println("Emitted: $it")
        emit(it)
        delay(1000)
    }
}