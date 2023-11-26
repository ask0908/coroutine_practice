package com.example.kotlinprac.pastcampus.security_keypad.util

import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

object ViewUtil {
    fun EditText.setOnEditorActionListener(action: Int, invoke: () -> Unit) {
        setOnEditorActionListener { _, actionId, _ ->
            if (actionId == action) {
                invoke()
                true
            } else {
                false
            }
        }
    }

    fun EditText.showKeyboard() {
        this.requestFocus()
        val inputMethodManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(this, InputMethodManager.SHOW_IMPLICIT)
    }

    // 키보드가 표시될 때 지연이 조금 있어야 키보드를 띄울 수 있음
    fun EditText.showKeyboardDelay() {
        postDelayed({
            showKeyboard()
        }, 200)
    }

    fun EditText.hideKeyboard() {
        this.clearFocus()
        val inputMethodManager =
            this.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(this.windowToken, 0)
    }
}