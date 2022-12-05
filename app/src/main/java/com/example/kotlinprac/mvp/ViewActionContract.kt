package com.example.kotlinprac.mvp

interface ViewActionContract {
    fun updateUI(message: String)
    fun getUserInformation()
}