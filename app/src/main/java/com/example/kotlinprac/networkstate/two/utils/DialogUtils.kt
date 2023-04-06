package com.example.kotlinprac.networkstate.two.utils

import android.app.AlertDialog
import android.content.Context

class DialogUtils(val context: Context) {
    fun showDialog(title: String, message: String): AlertDialog =
        AlertDialog.Builder(context).apply {
            setTitle(title)
            setMessage(message)
            setPositiveButton("OK") { dialog, _ ->
                dialog.dismiss()
            }
        }.show()
}