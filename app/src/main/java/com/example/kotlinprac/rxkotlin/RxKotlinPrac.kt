package com.example.kotlinprac.rxkotlin

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable

/* RxKotlin 작동 테스트 */
fun main() {
    val list: List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f)
//    val iterator = list.iterator()
//    while (iterator.hasNext()) {
//        println(iterator.next())
//    }
    var observable: Observable<Any> = list.toObservable()
    observable.subscribeBy(
        onNext = { println(it) },
        onError = { it.printStackTrace() },
        onComplete = { println("끝") }
    )
}