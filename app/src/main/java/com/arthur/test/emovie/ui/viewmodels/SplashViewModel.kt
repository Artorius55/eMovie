package com.arthur.test.emovie.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.test.emovie.data.remote.response.GenresResponse
import com.arthur.test.emovie.data.repository.ConfigurationRepository
import com.arthur.test.emovie.data.repository.DataResult
import com.arthur.test.emovie.data.repository.GenresRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val genresRepository: GenresRepository,
    private val configurationRepository: ConfigurationRepository,
) : ViewModel() {

    private val _genres: StateFlow<DataResult<GenresResponse>?> =
        genresRepository.getGenres().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            DataResult.Loading()
        )

    private val _imageUrlsSaved: StateFlow<Boolean> =
        configurationRepository.getAndSaveConfiguration().stateIn(
            viewModelScope,
            SharingStarted.Lazily,
            false
        )

    private val _dataLoaded: Flow<Boolean> = combine(_genres, _imageUrlsSaved) { genresResponse, imagesSaved ->
        genresResponse != null && imagesSaved
    }
    val dataLoaded: StateFlow<Boolean> =
        _dataLoaded.stateIn(viewModelScope, SharingStarted.Lazily, false)


}