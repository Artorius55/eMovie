package com.arthur.test.emovie.data.model

import androidx.room.Entity

@Entity(primaryKeys = ["movieId", "genreId"])
data class MovieWithGenreCrossRef(
    val movieId: Int,
    val genreId: Int,
)
