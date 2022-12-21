package com.arthur.test.emovie.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Video(
    val id: String,
    val name: String,
    val key: String,
    val type: String,
    val site: String,
) : Parcelable
