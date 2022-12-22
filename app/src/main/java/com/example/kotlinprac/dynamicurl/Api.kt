package com.example.kotlinprac.dynamicurl

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
annotation class Api(
    val value: ApiType
)
