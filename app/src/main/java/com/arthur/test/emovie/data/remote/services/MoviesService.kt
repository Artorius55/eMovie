package com.arthur.test.emovie.data.remote.services

import com.arthur.test.emovie.data.remote.response.MovieVideosResponse
import com.arthur.test.emovie.data.remote.response.MoviesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesService {

    @GET("movie/upcoming")
    suspend fun getUpcomingMovies(): Response<MoviesResponse>

    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(): Response<MoviesResponse>

    @GET("movie/{movieId}/videos")
    suspend fun getVideos(@Path("movieId") movieId: Int): Response<MovieVideosResponse>
}