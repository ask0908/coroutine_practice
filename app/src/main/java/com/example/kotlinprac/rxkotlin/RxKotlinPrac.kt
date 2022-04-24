package com.example.kotlinprac.rxkotlin

import io.reactivex.Observable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.rxkotlin.toObservable
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*

/* RxKotlin 작동 테스트 */
fun main(args: Array<String>) {
//    val list: List<Any> = listOf("One", 2, "Three", "Four", 4.5, "Five", 6.0f)
////    val iterator = list.iterator()
////    while (iterator.hasNext()) {
////        println(iterator.next())
////    }
//
//    // 1. 리스트 생성
//    // 2. 1에서 만든 리스트로 observable 인스턴스 생성
//    // 3. observable 인스턴스를 구독(named arguments 사용)
//    val observable: Observable<Any> = list.toObservable()
//    observable.subscribeBy(
//        onNext = { println(it) },               // 모든 데이터가 여기로 push됨
//        onError = { it.printStackTrace() },     // 모든 데이터가 push됐을 때 여기 호출
//        onComplete = { println("끝") }          // 에러 발생 시 여기 호출
//    )

//    println("기본값 a = 15, b = 10")
//    val calcurator = ReactiveCalculator(15, 10)
//    println("Enter a = <number> or b = <number> in separate lines\nexit to exit the program")
//    var line: String?
//    do {
//        line = readLine()
//        calcurator.handleInput(line)
//    }   while (line != null && !line.lowercase(Locale.getDefault()).contains("exit"))

//    val sum = { x: Int, y: Int -> x + y }
//    println("Sum ${sum(12, 14)}")
//    val anonymousMult = { x: Int -> (Random().nextInt(15) + 1) * x }    // 1~15까지 제한된 난수를 전달된 x와 곱하고 결과를 반환하는 다른 람다 선언
//    println("Random output ${anonymousMult(2)}")

    println("named func square = ${square(3)}")
    val qube = {n: Int -> n * n * n}
    println("Lambda pure func qube = ${qube(3)}")
}

fun square(n: Int): Int = n * n