package com.example.kotlinprac.coroutine_and_hilt.di

import android.util.Log
import com.example.kotlinprac.coroutine_and_hilt.ApiInterface
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {
    private val TAG = this.javaClass.simpleName

    @Provides
    @Singleton
    fun provideGson() = GsonBuilder().setLenient().create()!!

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val interceptor = HttpLoggingInterceptor { message: String ->
            Log.e("HttpLoggingInterceptor", "message : $message")
        }
        return interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): ApiInterface = Retrofit.Builder()
        .baseUrl("https://jsonplaceholder.typicode.com/")
        .client(provideOkHttpClient())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(client)
        .build()
        .create(ApiInterface::class.java)
}