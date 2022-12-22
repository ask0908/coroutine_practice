package com.example.kotlinprac.dynamicurl

/* https://proandroiddev.com/dynamic-multiple-base-url-with-annotations-android-f3af03e3cd4 */
object EnvironmentManager {
    private val environments = mutableListOf(
        EnvironmentModel(
            apiType = ApiType.DEV,
            DeploymentType.BETA
        ),
        EnvironmentModel(
            apiType = ApiType.PRODUCT,
            DeploymentType.PRODUCTION
        ),
    )

    fun getBaseUrl(apiType: ApiType) = environments.find { it.apiType == apiType }!!.baseUrl
}