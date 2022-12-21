package com.arthur.test.emovie.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.data.model.MovieGenre
import com.arthur.test.emovie.data.model.Video
import com.arthur.test.emovie.data.remote.response.MoviesResponse
import com.arthur.test.emovie.data.repository.ConfigurationRepository
import com.arthur.test.emovie.data.repository.DataResult
import com.arthur.test.emovie.data.repository.MoviesRepository
import com.arthur.test.emovie.utils.ext.getYearFromDate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MoviesViewModel @Inject constructor(
    private val repository: MoviesRepository,
    private val configurationRepository: ConfigurationRepository
) : ViewModel() {

    private val _selectedFilter: MutableStateFlow<FilterTypes> = MutableStateFlow(FilterTypes.LANG)
    val selectedFilter: StateFlow<FilterTypes> = _selectedFilter

    /* Flows for Movie List */
    private val _upcomingMovies: Flow<DataResult<MoviesResponse>?> = repository.getUpcomingMovies()
    val upcomingMovies: StateFlow<DataResult<MoviesResponse>?> =
        _upcomingMovies.stateIn(viewModelScope, SharingStarted.Lazily, DataResult.Loading())

    private val _topRatedMovies: Flow<DataResult<MoviesResponse>?> = repository.getTopRatedMovies()
    val topRatedMovies: StateFlow<DataResult<MoviesResponse>?> =
        _topRatedMovies.stateIn(viewModelScope, SharingStarted.Lazily, DataResult.Loading())

    private val _mediumSizeImageUrl: Flow<String?> = configurationRepository.getMediumSizeImageUrl()
    val mediumSizeImageUrl: StateFlow<String?> =
        _mediumSizeImageUrl.stateIn(viewModelScope, SharingStarted.Lazily, null)

    private val _selectedMovies: Flow<List<Movie>?> =
        combine(_selectedFilter, _topRatedMovies) { selected, topResult ->
            topResult?.data?.results?.filter {
                if (selected == FilterTypes.LANG) {
                    it.originalLanguage == "en"
                } else {
                    it.releaseDate?.contains("2008") ?: false
                }
            }?.take(6)
        }
    val selectedMovies: StateFlow<List<Movie>?> =
        _selectedMovies.stateIn(viewModelScope, SharingStarted.Lazily, listOf())

    /* FLOWS for Movie Details */
    private val _movieWithGenres: MutableStateFlow<MovieGenre?> = MutableStateFlow(null)
    private val _originalSizeImageUrl: Flow<String?> =
        configurationRepository.getOriginalSizeImageUrl()

    private val _detailedData: Flow<Pair<String?, MovieGenre?>> =
        combine(_originalSizeImageUrl, _movieWithGenres) { url, movie ->
            Pair(url, movie)
        }
    val detailedData: StateFlow<Pair<String?, MovieGenre?>> =
        _detailedData.stateIn(viewModelScope, SharingStarted.Lazily, Pair(null, null))

    private val _movieTrailer: MutableStateFlow<Video?> = MutableStateFlow(null)
    val movieTrailer: StateFlow<Video?> = _movieTrailer

    fun selectFilter(type: FilterTypes) {
        _selectedFilter.value = type
    }

    fun setSelectMovie(movie: Movie) = viewModelScope.launch(Dispatchers.IO) {
        _movieWithGenres.value = repository.getMovieWithGenres(movie.id)

        val trailerResult = repository.getMovieVideos(movie.id)

        if (trailerResult is DataResult.Success) {
            trailerResult.data?.results?.let { videos ->
                _movieTrailer.value = videos.firstOrNull {
                    it.type == "Trailer"
                }
            }
        }
    }
}

enum class FilterTypes { LANG, DATE }