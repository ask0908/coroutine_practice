package com.example.kotlinprac.dynamicurl

data class EnvironmentModel(
    val apiType: ApiType,
    val deploymentType: DeploymentType
) {
    var baseUrl = ""

    init {
        setBaseUrl(apiType, deploymentType)
    }

    fun setBaseUrl(
        apiType: ApiType,
        deploymentType: DeploymentType
    ) {
        val prefix = when (deploymentType) {
            DeploymentType.ALPHA -> "alpha-"
            DeploymentType.BETA -> "beta-"
            DeploymentType.PRODUCTION -> ""
        }
        baseUrl = "https://$prefix${apiType.url}"
    }
}
