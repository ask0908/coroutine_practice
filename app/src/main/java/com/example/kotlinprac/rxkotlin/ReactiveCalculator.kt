package com.example.kotlinprac.rxkotlin

import android.annotation.SuppressLint
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

@SuppressLint("CheckResult")
class ReactiveCalculator(firstNumber: Int, secondNumber: Int) {
    // 사칙연산에 사용할 Subject 생성
    private val subjectAdd: Subject<Pair<Int, Int>> = PublishSubject.create()
    private val subjectSub: Subject<Pair<Int, Int>> = PublishSubject.create()
    private val subjectMult: Subject<Pair<Int, Int>> = PublishSubject.create()
    private val subjectDiv: Subject<Pair<Int, Int>> = PublishSubject.create()

    private val subjectCalc: Subject<ReactiveCalculator> = PublishSubject.create()

    private var nums: Pair<Int, Int> = Pair(0, 0)

    init {
        // Pair 안의 값을 입력받은 값으로 초기화
        // 처음 시작 시에만 호출된다
        nums = Pair(firstNumber, secondNumber)

        // 사용자가 입력하는 값의 변경사항 관찰 후 처리
        subjectAdd.map { it.first + it.second }.subscribe { println("Add = $it") }
        subjectSub.map { it.first - it.second }.subscribe { println("Subtract = $it") }
        subjectMult.map { it.first * it.second }.subscribe { println("Multiply = $it") }
        subjectDiv.map { it.first / (it.second * 1.0) }.subscribe { println("Divide = $it") }

        subjectCalc.subscribe {
            with(it) {
                calculateAddition()
                calculateSubtraction()
                calculateMultiplication()
                calculateDivision()
            }
        }
    }

    private fun calculateAddition() {
        subjectAdd.onNext(nums)
    }

    private fun calculateSubtraction() {
        subjectSub.onNext(nums)
    }

    private fun calculateMultiplication() {
        subjectMult.onNext(nums)
    }

    private fun calculateDivision() {
        subjectDiv.onNext(nums)
    }

    // 입력받은 2개의 숫자를 받아서 Pair로 바꾼 후 사칙연산 수행
    private fun modifyNumbers(firstNumber: Int = nums.first, secondNumber: Int = nums.second) {
        nums = Pair(firstNumber, secondNumber)
        subjectCalc.onNext(this)
    }

    fun handleInput(inputLine: String?) {
        if (!inputLine.equals("exit")) {
            val pattern: Pattern = Pattern.compile("([a|b])(?:\\s)?=(?:\\s)?(\\d*)")
            var a: Int? = null
            var b: Int? = null

            val matcher: Matcher = pattern.matcher(inputLine)

            // a=10 형태로 입력하기 때문에 matcher.group(1) : a / matcher.group(2) : 10 식으로 출력된다
            if (matcher.matches() && matcher.group(1) != null && matcher.group(2) != null) {
                // 사용자가 "a=10", "b=20" 식으로 입력했는지 여부를 확인해서 계산할 1번째 숫자, 2번째 숫자를 가져오는 로직
                if (matcher.group(1).lowercase(Locale.getDefault()) == "a") {
                    a = matcher.group(2).toInt()
                } else if (matcher.group(1).lowercase(Locale.getDefault()) == "b") {
                    b = matcher.group(2).toInt()
                }
            }

            when {
                a != null && b != null -> modifyNumbers(a, b)
                a != null -> modifyNumbers(firstNumber = a)
                b != null -> modifyNumbers(secondNumber = b)
                else -> println("잘못된 input입니다")
            }
        }
    }

}