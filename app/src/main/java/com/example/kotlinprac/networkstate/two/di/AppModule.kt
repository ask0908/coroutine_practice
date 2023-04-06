package com.example.kotlinprac.networkstate.two.di

import com.example.kotlinprac.BuildConfig
import com.example.kotlinprac.networkstate.two.data.network.NewsService
import com.example.kotlinprac.networkstate.two.di.util.AuthenticationInterceptor
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(BuildConfig.NEWS_API_BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()

    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Singleton
    @Provides
    fun provideAuthenticationInterceptor(): AuthenticationInterceptor = AuthenticationInterceptor()

    @Singleton
    @Provides
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return httpLoggingInterceptor
    }

    @Singleton
    @Provides
    fun provideHttpClient(
        logging: HttpLoggingInterceptor,
        auth: AuthenticationInterceptor
    ): OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(logging)
        .addInterceptor(auth)
        .connectTimeout(1, TimeUnit.MINUTES)
        .readTimeout(1, TimeUnit.MINUTES)
        .writeTimeout(1, TimeUnit.MINUTES)
        .build()

    @Singleton
    @Provides
    fun provideCommonService(retrofit: Retrofit): NewsService =
        retrofit.create(NewsService::class.java)
}