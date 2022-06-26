package com.example.kotlinprac.coroutine_and_hilt

import android.app.Application
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication: Application() {
    var instance: BaseApplication? = null
    private lateinit var context: Context
    private val TAG: String = this.javaClass.simpleName

    override fun onCreate() {
        context = this
        instance = this
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
        instance = null
    }

}