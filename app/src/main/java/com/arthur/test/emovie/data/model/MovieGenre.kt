package com.arthur.test.emovie.data.model

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class MovieGenre(
    @Embedded val movie: Movie,
    @Relation(
        parentColumn = "movieId",
        entityColumn = "genreId",
        associateBy = Junction(MovieWithGenreCrossRef::class)
    )
    val genres: List<Genre>,
)
