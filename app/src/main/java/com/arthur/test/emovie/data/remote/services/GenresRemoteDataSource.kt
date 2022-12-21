package com.arthur.test.emovie.data.remote.services

import com.arthur.test.emovie.data.remote.response.BaseResponse
import javax.inject.Inject

class GenresRemoteDataSource @Inject constructor(private val genresService: GenresService) :
    BaseResponse() {
    suspend fun getGenres() = getResult { genresService.getGenres() }
}