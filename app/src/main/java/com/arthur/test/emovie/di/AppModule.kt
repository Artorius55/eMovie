package com.arthur.test.emovie.di

import android.content.Context
import com.arthur.test.emovie.data.local.AppDatabase
import com.arthur.test.emovie.data.local.dao.GenreDao
import com.arthur.test.emovie.data.local.dao.MovieDao
import com.arthur.test.emovie.data.remote.response.ConfigurationRemoteDataSource
import com.arthur.test.emovie.data.remote.services.*
import com.arthur.test.emovie.data.remote.utils.ApiInterceptor
import com.arthur.test.emovie.data.repository.ConfigurationRepository
import com.arthur.test.emovie.data.repository.GenresRepository
import com.arthur.test.emovie.data.repository.MoviesRepository
import com.arthur.test.emovie.utils.datastore.ImageUrlsDataStore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"
    private const val API_KEY = "6b30402fc745f0c51f5c865a16755164"

    @Singleton
    @Provides
    fun provideRetrofit(gson: Gson): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(ApiInterceptor(API_KEY))
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }


    @Provides
    fun provideGson(): Gson = GsonBuilder().create()

    @Provides
    fun provideMoviesService(retrofit: Retrofit): MoviesService =
        retrofit.create(MoviesService::class.java)

    @Provides
    fun provideGenreService(retrofit: Retrofit): GenresService =
        retrofit.create(GenresService::class.java)

    @Provides
    fun provideConfigurationService(retrofit: Retrofit): ConfigurationService =
        retrofit.create(ConfigurationService::class.java)

    @Singleton
    @Provides
    fun provideMoviesRemoteDataSource(moviesService: MoviesService) =
        MoviesRemoteDataSource(moviesService)

    @Singleton
    @Provides
    fun provideGenresRemoteDataSource(genresService: GenresService) =
        GenresRemoteDataSource(genresService)

    @Singleton
    @Provides
    fun provideConfigurationRemoteDataSource(configurationService: ConfigurationService) =
        ConfigurationRemoteDataSource(configurationService)

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext appContext: Context) =
        AppDatabase.getDatabase(appContext)

    @Singleton
    @Provides
    fun provideMovieDao(db: AppDatabase) = db.movieDao()

    @Singleton
    @Provides
    fun provideGenreDao(db: AppDatabase) = db.genreDao()

    @Singleton
    @Provides
    fun provideMoviesRepository(
        remoteDataSource: MoviesRemoteDataSource,
        localDataSource: MovieDao
    ) = MoviesRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideGenresRepository(
        remoteDataSource: GenresRemoteDataSource,
        localDataSource: GenreDao
    ) = GenresRepository(remoteDataSource, localDataSource)

    @Singleton
    @Provides
    fun provideConfigurationRepository(
        remoteDataSource: ConfigurationRemoteDataSource,
        localDataSource: ImageUrlsDataStore
    ) = ConfigurationRepository(remoteDataSource, localDataSource)

}