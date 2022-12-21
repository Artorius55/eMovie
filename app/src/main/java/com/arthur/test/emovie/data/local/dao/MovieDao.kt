package com.arthur.test.emovie.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.data.model.MovieGenre
import com.arthur.test.emovie.data.model.MovieWithGenreCrossRef

@Dao
interface MovieDao {

    //MovieDao
    @Query("SELECT * FROM movies")
    fun getAllMovies() : List<Movie>

    @Query("SELECT * FROM movies WHERE movieId = :mId")
    fun getMovie(mId: Int): Movie

    @Query("SELECT * FROM movies WHERE isUpcomming = 1")
    fun getUpcomingMovies() : List<Movie>

    @Query("SELECT * FROM movies WHERE isTopRated = 1")
    fun getTopRatedMovies() : List<Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(movies: List<Movie>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(movie: Movie)

    @Update
    fun updateMovie(movie: Movie)

    @Update
    fun updateSongs(movies: List<Movie>): Int

    @Query("UPDATE movies SET isTopRated = :isTopRated WHERE movieId IN (:ids)")
    fun setMoviesAsTopRated(ids:List<Int>, isTopRated:Boolean)

    //Relationship methods
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllWithGenre(movieWithGenreCrossRef: List<MovieWithGenreCrossRef>)

    @Transaction
    @Query("SELECT * FROM movies WHERE movieId = :mId")
    fun getMovieWithGenre(mId: Int): MovieGenre

}