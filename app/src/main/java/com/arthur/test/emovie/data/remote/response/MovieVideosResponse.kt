package com.arthur.test.emovie.data.remote.response

import com.arthur.test.emovie.data.model.Video

data class MovieVideosResponse(
    val id: Int,
    val results: List<Video>?,
)
