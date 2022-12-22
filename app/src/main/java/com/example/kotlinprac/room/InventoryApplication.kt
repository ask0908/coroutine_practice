package com.example.kotlinprac.room

import android.app.Application
import coil.ImageLoader
import coil.ImageLoaderFactory
import com.example.kotlinprac.room.data.ItemRoomDatabase

class InventoryApplication: Application(), ImageLoaderFactory {
    // 위임을 쓰기 때문에 참조가 처음 필요하거나 참조에 처음 접근할 때(앱 시작 시 x) database 인스턴스가 느리게 만들어진다
    // -> 처음 접근 시 DB가 만들어진다. 나중에 뷰모델 인스턴스 만들 때 이 인스턴스 사용
    val database: ItemRoomDatabase by lazy {
        ItemRoomDatabase.getDatabase(this)
    }

    override fun newImageLoader(): ImageLoader {
        return ImageLoader.Builder(this)
            .crossfade(true)
            .build()
    }
}