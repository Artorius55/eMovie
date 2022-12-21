package com.arthur.test.emovie.utils.datastore

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageUrlsDataStore @Inject constructor(@ApplicationContext private val context: Context) {

    private val Context.dataStore by preferencesDataStore(name = IMAGE_URL_DATASTORE)

    companion object {
        //Preferences name
        private const val IMAGE_URL_DATASTORE = "imageUrlDataStore"

        private val ORIGINAL_SIZE_URL = stringPreferencesKey("originalSizeUrl")
        private val MEDIUM_SIZE_URL = stringPreferencesKey("mediumSizeUrl")
    }

    suspend fun saveOriginalSizeUrl(url: String) {
        context.dataStore.edit {
            it[ORIGINAL_SIZE_URL] = url
        }
    }

    fun getOriginalSizeUrl(): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[ORIGINAL_SIZE_URL] ?: ""
    }

    suspend fun saveMediumSizeUrl(url: String) {
        context.dataStore.edit {
            it[MEDIUM_SIZE_URL] = url
        }
    }

    fun getMediumSizeUrl(): Flow<String> = context.dataStore.data.map { preferences ->
        preferences[MEDIUM_SIZE_URL] ?: ""
    }
}