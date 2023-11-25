package com.example.kotlinprac.pastcampus.security_keypad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PinViewModel: ViewModel() {
    private val _passwordLiveData by lazy { MutableLiveData<CharSequence>() }
    val passwordLiveData: LiveData<CharSequence> by lazy { _passwordLiveData }

    val password: StringBuffer = StringBuffer("")

    fun input(num: String) {
        if (password.length < 6) {
            password.append(num)
            _passwordLiveData.value = password.toString()
        }
    }

    fun delete() {
        if (password.isNotEmpty()) {
            password.deleteCharAt(password.length - 1)
            _passwordLiveData.value = password.toString()
        }
    }

    fun done() {
        // 0번 인덱스 문자부터 마지막 문자까지 전부 공백처리
        password.replace(0, password.length, "")
    }
}