package com.arthur.test.emovie.data.repository

import com.arthur.test.emovie.data.local.dao.GenreDao
import com.arthur.test.emovie.data.remote.response.GenresResponse
import com.arthur.test.emovie.data.remote.services.GenresRemoteDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GenresRepository @Inject constructor(
    private val remoteDataSource: GenresRemoteDataSource,
    private val localDataSource: GenreDao
) {

    fun getGenres() : Flow<DataResult<GenresResponse>?> = flow {
        emit(DataResult.Loading())
        val result = remoteDataSource.getGenres()
        if (result is DataResult.Success) {
            result.data?.genres?.let { it ->
                localDataSource.insertAll(it)
            }
        }
        emit(result)
    }.flowOn(Dispatchers.IO)
}