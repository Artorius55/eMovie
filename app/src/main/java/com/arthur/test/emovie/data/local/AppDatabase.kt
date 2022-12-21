package com.arthur.test.emovie.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.arthur.test.emovie.data.local.dao.GenreDao
import com.arthur.test.emovie.data.local.dao.MovieDao
import com.arthur.test.emovie.data.model.Genre
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.data.model.MovieWithGenreCrossRef

@Database(
    entities = [Movie::class, Genre::class, MovieWithGenreCrossRef::class],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
    abstract fun genreDao(): GenreDao

    companion object {
        @Volatile private var instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, AppDatabase::class.java, "movies")
                .fallbackToDestructiveMigration()
                .build()
    }
}