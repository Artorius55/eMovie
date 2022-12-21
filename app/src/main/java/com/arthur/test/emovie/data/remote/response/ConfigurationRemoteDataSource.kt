package com.arthur.test.emovie.data.remote.response

import com.arthur.test.emovie.data.remote.services.ConfigurationService
import javax.inject.Inject

class ConfigurationRemoteDataSource @Inject constructor(private val configurationService: ConfigurationService) :
    BaseResponse() {
    suspend fun getConfiguration() = getResult { configurationService.getConfiguration() }
}