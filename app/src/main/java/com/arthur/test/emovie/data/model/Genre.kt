package com.arthur.test.emovie.data.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "genres")
data class Genre(
    @PrimaryKey
    @ColumnInfo(name = "genreId")
    val id: Int,
    val name: String,
) : Parcelable
