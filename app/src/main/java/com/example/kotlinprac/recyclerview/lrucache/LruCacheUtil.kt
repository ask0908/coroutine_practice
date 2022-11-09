package com.example.kotlinprac.recyclerview.lrucache

import android.content.Context
import android.graphics.Bitmap
import android.util.Log
import android.util.LruCache
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.InputStream

class LruCacheUtil {
    companion object {
        const val MAX_SIZE: Int = 1024
    }
    val instance = LruCacheUtil()
    private val lru: LruCache<Any, Any> = LruCache(MAX_SIZE)

    fun saveImageToCache(bitmap: Bitmap, key: String) {
        this.instance.lru.put(key, bitmap)
    }

    fun getImageFromCache(key: String): Bitmap = (LruCacheUtil().instance.lru.get(key)) as Bitmap

    fun prepString(context: Context, fileName: String): String {
        var dummyString = ""
        var input: InputStream
        val assetManager = context.assets

        val ceh = CoroutineExceptionHandler { _, exception ->
            Log.e("exception!!", "exception : ${exception.message}")
        }
        CoroutineScope(ceh).launch {
            input = assetManager.open(fileName)

            val size: Int = input.available()
            val buffer: ByteArray = ByteArray(size)
            input.read(buffer)
            input.close()

            val text: String = buffer.toString()
            dummyString = buffer.toString()
        }

        return dummyString
    }

}