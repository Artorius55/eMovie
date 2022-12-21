package com.arthur.test.emovie.data.remote.services

import com.arthur.test.emovie.data.remote.response.GenresResponse
import retrofit2.Response
import retrofit2.http.GET

interface GenresService {
    @GET("genre/movie/list")
    suspend fun getGenres(): Response<GenresResponse>
}