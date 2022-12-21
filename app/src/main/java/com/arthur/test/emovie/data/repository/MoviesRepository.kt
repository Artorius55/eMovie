package com.arthur.test.emovie.data.repository

import com.arthur.test.emovie.data.local.dao.MovieDao
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.data.model.MovieGenre
import com.arthur.test.emovie.data.model.getMoviesWithGenreCrossRef
import com.arthur.test.emovie.data.remote.response.MovieVideosResponse
import com.arthur.test.emovie.data.remote.response.MoviesResponse
import com.arthur.test.emovie.data.remote.services.MoviesRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val remoteDataSource: MoviesRemoteDataSource,
    private val localDataSource: MovieDao
) {

    fun getAllMovies(): Flow<List<Movie>?> = flow {
        emit(localDataSource.getUpcomingMovies())
    }.flowOn(Dispatchers.IO)

    fun getUpcomingMovies(): Flow<DataResult<MoviesResponse>?> = flow {
        emit(DataResult.Loading())
        emit(fetchUpcomingMoviesCached())

        val result = remoteDataSource.getUpcomingMovies()
        if (result is DataResult.Success) {
            result.data?.results?.let { it ->
                it.forEach { movie ->
                    movie.isUpcomming = true
                }
                localDataSource.insertAll(it)
                val refs = it.getMoviesWithGenreCrossRef()
                localDataSource.insertAllWithGenre(refs)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun getTopRatedMovies(): Flow<DataResult<MoviesResponse>?> = flow {
        emit(DataResult.Loading())
        emit(fetchTopRatedMoviesCached())

        val result = remoteDataSource.getTopRatedMovies()
        if (result is DataResult.Success) {
            result.data?.results?.let { it ->
                it.forEach { movie ->
                    movie.isTopRated = true
                }
                localDataSource.insertAll(it)
                val refs = it.getMoviesWithGenreCrossRef()
                localDataSource.insertAllWithGenre(refs)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)

    fun getMovieWithGenres(movieId: Int): MovieGenre = localDataSource.getMovieWithGenre(movieId)

    suspend fun getMovieVideos(movieId: Int): DataResult<MovieVideosResponse> =
        remoteDataSource.getVideos(movieId)

    private fun fetchUpcomingMoviesCached(): DataResult<MoviesResponse>? =
        localDataSource.getUpcomingMovies()?.let {
            DataResult.Success(MoviesResponse(it))
        }

    private fun fetchTopRatedMoviesCached(): DataResult<MoviesResponse>? =
        localDataSource.getUpcomingMovies()?.let {
            DataResult.Success(MoviesResponse(it))
        }
}