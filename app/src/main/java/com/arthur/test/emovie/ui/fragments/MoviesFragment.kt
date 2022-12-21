package com.arthur.test.emovie.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.arthur.test.emovie.R
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.data.remote.response.MoviesResponse
import com.arthur.test.emovie.data.repository.DataResult
import com.arthur.test.emovie.databinding.FragmentMoviesBinding
import com.arthur.test.emovie.ui.adapters.ClickListenerAdapter
import com.arthur.test.emovie.ui.adapters.MovieAdapter
import com.arthur.test.emovie.ui.viewmodels.FilterTypes
import com.arthur.test.emovie.ui.viewmodels.MoviesViewModel
import com.arthur.test.emovie.utils.launchAndRepeatOnLifecycle
import com.arthur.test.emovie.utils.navigateToNext
import com.arthur.test.emovie.utils.observeFor
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : BaseFragment(), ClickListenerAdapter<Movie> {

    private var _binding: FragmentMoviesBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MoviesViewModel>()

    private val upcomingAdapter = MovieAdapter(this)
    private val topAdapter = MovieAdapter(this)
    private val selectedAdapter = MovieAdapter(this)

    override fun onStart() {
        super.onStart()
        safeActivity.supportActionBar?.hide()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMoviesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponents()
        collectViewModel()
    }

    /**
     * Function to init view components of this Fragment
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun initComponents() {
        with(binding) {
            rvUpcomingMovies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvUpcomingMovies.adapter = upcomingAdapter

            rvTopMovies.layoutManager =
                LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            rvTopMovies.adapter = topAdapter

            rvSelected.layoutManager =
                GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
            rvSelected.adapter = selectedAdapter

            rgFilter.setOnCheckedChangeListener { _, i -> onButtonClicked(i) }

        }

    }

    /**
     * Observe the [viewModel] events.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun collectViewModel() = launchAndRepeatOnLifecycle(state = Lifecycle.State.STARTED) {
        observeFor(viewModel.mediumSizeImageUrl, ::urlCollector)
        observeFor(viewModel.upcomingMovies, ::upcomingMoviesCollector)
        observeFor(viewModel.topRatedMovies, ::topMoviesCollector)
        observeFor(viewModel.selectedMovies, ::selectedMoviesCollector)
    }

    /**
     * Observe the [imgUrl] object from view model.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun urlCollector(imgUrl: String?) {
        upcomingAdapter.setImageUrl(imgUrl)
        topAdapter.setImageUrl(imgUrl)
        selectedAdapter.setImageUrl(imgUrl)
    }

    /**
     * Observe the [result] object from view model.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun upcomingMoviesCollector(result: DataResult<MoviesResponse>?) {
        when (result) {
            is DataResult.Success -> {
                upcomingAdapter.setData(result.data?.results)
            }
            is DataResult.Loading -> {

            }
            else -> {
                //error or something else
            }
        }
    }

    /**
     * Observe the [result] object of topRatedMovies from view model.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun topMoviesCollector(result: DataResult<MoviesResponse>?) {
        when (result) {
            is DataResult.Success -> {
                topAdapter.setData(result.data?.results)
            }
            is DataResult.Loading -> {

            }
            else -> {
                //error or something else
            }
        }
    }

    private fun selectedMoviesCollector(movies: List<Movie>?){
        selectedAdapter.setData(movies)
    }

    private fun onButtonClicked(checkdedId: Int){
        val selected = when(checkdedId){
            R.id.rb_lang -> FilterTypes.LANG
            else -> FilterTypes.DATE
        }

        viewModel.selectFilter(selected)
    }

    /**
     * Fun to set selected [movie] into [viewModel] and navigate to right screen
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    override fun onItemClicked(position: Int, movie: Movie) {
        viewModel.setSelectMovie(movie)
        navigateToNext()
    }

    /**
     * Fun to navigates to next screen
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun navigateToNext() {
        findNavController().navigateToNext(
            R.id.moviesFragment,
            MoviesFragmentDirections.actionMoviesFragmentToDetailMovieFragment()
        )
    }

}