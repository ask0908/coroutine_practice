package com.example.kotlinprac.coroutine_and_hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @ApplicationScope
    @Provides
    @Singleton
    // SupervisorJob으로 코루틴 범위에서 명시적으로 비동기 작업을 수행하는 경우에만 작동하게 해서 충돌을 방지한다
    // 비동기 내부에서 에러 발생 시 외부 범위를 건드리지 않고 이 범위에서 생성된 모든 코루틴을 취소한다
    fun provideApplicationScope(): CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
}

/* 어노테이션 커스텀. 런타임에 의존성 주입을 수행하도록 어노테이션을 합쳐 새 커스텀 어노테이션을 만든다 */
@Retention(AnnotationRetention.RUNTIME)
@Qualifier                                  // 모듈 사용 시 똑같은 리턴타입이 있을 경우 사용하는 한정자 어노테이션. 방식은 다르지만 @Named로 같은 기능을 할 수도 있다
annotation class ApplicationScope