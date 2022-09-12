package com.example.kotlinprac.mapper.data.di

import android.util.Log
import com.example.kotlinprac.mapper.data.remote.ApiService
import com.example.kotlinprac.mapper.data.repository.CharactersRepo
import com.example.kotlinprac.mapper.data.repository.CharactersRepoImpl
import com.example.kotlinprac.mapper.ui.BASE_URL
import com.example.kotlinprac.other.GithubService
import com.example.kotlinprac.recyclerview.bindingadapter.ImageApiInterface
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
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
object AppModule {

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
    fun providesRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit
        .Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .build()

    @Provides
    @Singleton
    fun apiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Provides
    @Singleton
    fun providesCharactersRepo(apiService: ApiService): CharactersRepo = CharactersRepoImpl(apiService)

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(provideLoggingInterceptor())
        .build()

    @Provides
    @Singleton
    fun provideGithubApiClient(client: OkHttpClient): GithubService = Retrofit.Builder()
        .baseUrl("http://api.github.com")
        .client(provideOkHttpClient())
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(client)
        .build()
        .create(GithubService::class.java)

    @Provides
    @Singleton
    fun provideImageListApiClient(client: OkHttpClient): ImageApiInterface = Retrofit.Builder()
        .baseUrl("https://api.unsplash.com/")
        .addConverterFactory(ScalarsConverterFactory.create())
        .addConverterFactory(GsonConverterFactory.create(provideGson()))
        .client(client)
        .build()
        .create(ImageApiInterface::class.java)

}