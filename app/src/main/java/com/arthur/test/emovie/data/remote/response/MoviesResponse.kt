package com.arthur.test.emovie.data.remote.response

import com.arthur.test.emovie.data.model.Movie

data class MoviesResponse(
    val results: List<Movie>,
)
