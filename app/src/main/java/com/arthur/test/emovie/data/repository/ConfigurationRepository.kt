package com.arthur.test.emovie.data.repository

import com.arthur.test.emovie.data.remote.response.ConfigurationRemoteDataSource
import com.arthur.test.emovie.utils.datastore.ImageUrlsDataStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class ConfigurationRepository @Inject constructor(
    private val remoteDataSource: ConfigurationRemoteDataSource,
    private val localDataSource: ImageUrlsDataStore,
) {

    fun getAndSaveConfiguration(): Flow<Boolean> = flow {
        val result = remoteDataSource.getConfiguration()
        if (result is DataResult.Success) {
            result.data?.images?.let { it ->
                val originalSizeUrl = it.secureBaseUrl + it.posterSizes.lastOrNull()
                val mediumSizeUrl = it.secureBaseUrl + it.posterSizes[it.posterSizes.size / 2]

                localDataSource.saveMediumSizeUrl(mediumSizeUrl)
                localDataSource.saveOriginalSizeUrl(originalSizeUrl)
                emit(true)
            }
        } else {
            emit(true)
        }
    }.flowOn(Dispatchers.IO)

    fun getOriginalSizeImageUrl(): Flow<String> =
        localDataSource.getOriginalSizeUrl().flowOn(Dispatchers.IO)

    fun getMediumSizeImageUrl(): Flow<String> =
        localDataSource.getMediumSizeUrl().flowOn(Dispatchers.IO)
}