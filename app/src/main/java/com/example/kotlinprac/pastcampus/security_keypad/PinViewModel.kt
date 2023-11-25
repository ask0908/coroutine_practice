package com.example.kotlinprac.pastcampus.security_keypad

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.regex.Pattern

class PinViewModel: ViewModel() {
    private val _passwordLiveData by lazy { MutableLiveData<CharSequence>() }
    val passwordLiveData: LiveData<CharSequence> by lazy { _passwordLiveData }

    private val _messageLiveData by lazy { MutableLiveData<String>() }
    val messageLiveData: LiveData<String> by lazy { _messageLiveData }

    private val password: StringBuffer = StringBuffer("")

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
        if (validPin()) {
            _messageLiveData.value = "설정한 비밀번호는 $password 입니다"
            reset()
        }
        // 0번 인덱스 문자부터 마지막 문자까지 전부 공백처리
        password.replace(0, password.length, "")
        _passwordLiveData.value = password.toString()
    }

    private fun reset() {
        // 0번 인덱스 문자부터 마지막 문자까지 전부 공백처리
        password.replace(0, password.length, "")
        _passwordLiveData.value = password.toString()
    }

    private fun validPin(): Boolean {
        if (password.length < 6) {
            _messageLiveData.value = "비밀번호 6자리를 입력해 주세요"
            return false
        }
        // 3자리 연속 같은 숫자는 허용하지 않음
        if (Pattern.compile("(\\w)\\1\\1").matcher(password.toString()).find()) {
            _messageLiveData.value = "3자리 이상 같은 숫자는 사용하실 수 없습니다"
            reset()
            return false
        }
        // 연속 숫자 체크 : reduce 사용
        var count = 0
        password.reduce { before, after ->
            if (after - before == 1) {
                count++
                if (count >= 2) {
                    // count가 3번 연속되면
                    _messageLiveData.value = "연속된 3자리 숫자는 사용하실 수 없습니다"
                    reset()
                    return false
                }
            } else {
                count = 0
            }
            after
        }

        return true
    }
}