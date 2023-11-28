package com.example.kotlinprac.pastcampus.shopping_mall.di

import com.example.kotlinprac.pastcampus.shopping_mall.remote.MainService
import com.example.kotlinprac.pastcampus.shopping_mall.remote.repository.MainRepository
import com.example.kotlinprac.pastcampus.shopping_mall.remote.repository.MainRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class) // repository는 뷰모델에서 쓰기 때문에 컴포넌트를 다르게 쓴다
object MainRepositoryModule {
    @ViewModelScoped
    @Provides
    fun providesMainRepository(
        mainService: MainService
    ): MainRepository = MainRepositoryImpl(mainService)
}