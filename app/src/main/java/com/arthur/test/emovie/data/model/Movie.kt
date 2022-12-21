package com.arthur.test.emovie.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "movies")
data class Movie(
    @PrimaryKey
    @ColumnInfo(name = "movieId")
    val id: Int,
    val adult: Boolean,
    @SerializedName("backdrop_path")
    val backdropPath: String?,
    @SerializedName("original_language")
    val originalLanguage: String,
    @SerializedName("original_title")
    val originalTitle: String,
    val overview: String,
    @SerializedName("poster_path")
    val posterPath: String?,
    @SerializedName("release_date")
    val releaseDate: String?,
    val title: String,
    val video: Boolean,
    @SerializedName("vote_average")
    val voteAverage: Float,
    var isTopRated: Boolean = false,
    var isUpcomming: Boolean = false,
) : Parcelable {
    @Ignore
    @SerializedName("genre_ids")
    var genres: List<Int>? = listOf()
}

/**
 * Extension function to get a list of Movies cross reference with Genres entity
 *
 * @author: Arturo Segura
 * @since: 1.0
 */
fun List<Movie>.getMoviesWithGenreCrossRef(): List<MovieWithGenreCrossRef> {
    val data = mutableListOf<MovieWithGenreCrossRef>()
    this.forEach { movie ->
        movie.genres?.let {
            data.addAll(
                it.map { genreId ->
                    MovieWithGenreCrossRef(movie.id, genreId)
                }
            )
        }
    }
    return data
}