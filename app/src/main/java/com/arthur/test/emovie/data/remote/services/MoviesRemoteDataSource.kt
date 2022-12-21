package com.arthur.test.emovie.data.remote.services

import com.arthur.test.emovie.data.remote.response.BaseResponse
import javax.inject.Inject

class MoviesRemoteDataSource @Inject constructor(private val moviesService: MoviesService) : BaseResponse() {
    suspend fun getUpcomingMovies() = getResult { moviesService.getUpcomingMovies() }
    suspend fun getTopRatedMovies() = getResult { moviesService.getTopRatedMovies() }
    suspend fun getVideos(movieId: Int) = getResult { moviesService.getVideos(movieId) }
}