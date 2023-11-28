package com.example.kotlinprac.pastcampus.shopping_mall.di

import com.example.kotlinprac.pastcampus.shopping_mall.model.ListItem
import com.example.kotlinprac.pastcampus.shopping_mall.remote.ListItemDeserializer
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.sql.Time
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {
    @Provides
    @Singleton
    fun provideConverterFactory(): GsonConverterFactory = GsonConverterFactory.create(
        GsonBuilder()
            // viewtype이 있는 아이템이 들어오면 ListItemDeserializer에 정의돼 있을 경우 해당 data class를 리턴할 수 있게 된다
            .registerTypeAdapter(ListItem::class.java, ListItemDeserializer())
            .create()
    )

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient.Builder = OkHttpClient.Builder().apply {
        connectTimeout(5, TimeUnit.SECONDS)
        readTimeout(5, TimeUnit.SECONDS)
        writeTimeout(5, TimeUnit.SECONDS)
    }

    @Provides
    @Singleton
    fun provideRetrofit(
        client: OkHttpClient.Builder,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.31.16:3030/api/v1/fastcampus/")
        .addConverterFactory(gsonConverterFactory)
        .client(client.build())
        .build()
}