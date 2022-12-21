package com.arthur.test.emovie.data.remote.utils

import okhttp3.Interceptor
import okhttp3.Response

class ApiInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalRequest = chain.request()

        // Add the api_key query parameter to the request
        val requestWithApiKey = originalRequest.newBuilder()
            .url(
                originalRequest.url()
                    .newBuilder()
                    .addQueryParameter("api_key", apiKey)
                    .build()
            )
            .build()

        return chain.proceed(requestWithApiKey)
    }
}