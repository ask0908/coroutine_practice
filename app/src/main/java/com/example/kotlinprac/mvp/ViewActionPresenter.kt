package com.example.kotlinprac.mvp

import android.content.Context
import android.widget.Toast

class ViewActionPresenter(
    private val context: Context
): ViewActionContract {
    override fun updateUI(message: String) {
        Toast.makeText(context, "updateUI() 호출. message : $message", Toast.LENGTH_SHORT).show()
    }

    override fun getUserInformation() {
        Toast.makeText(context, "getUserInformation() 호출", Toast.LENGTH_SHORT).show()
    }
}