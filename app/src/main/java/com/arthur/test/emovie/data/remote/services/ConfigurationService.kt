package com.arthur.test.emovie.data.remote.services

import com.arthur.test.emovie.data.remote.response.ConfigurationResponse
import retrofit2.Response
import retrofit2.http.GET

interface ConfigurationService {
    @GET("configuration")
    suspend fun getConfiguration(): Response<ConfigurationResponse>
}