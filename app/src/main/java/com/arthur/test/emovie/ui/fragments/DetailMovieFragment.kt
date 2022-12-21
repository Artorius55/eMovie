package com.arthur.test.emovie.ui.fragments

import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.widget.TextViewCompat
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.arthur.test.emovie.R
import com.arthur.test.emovie.data.model.Genre
import com.arthur.test.emovie.data.model.MovieGenre
import com.arthur.test.emovie.data.model.Video
import com.arthur.test.emovie.databinding.FragmentDetailMovieBinding
import com.arthur.test.emovie.ui.viewmodels.MoviesViewModel
import com.arthur.test.emovie.utils.ext.getYearFromDate
import com.arthur.test.emovie.utils.launchAndRepeatOnLifecycle
import com.arthur.test.emovie.utils.observeFor
import com.bumptech.glide.Glide
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailMovieFragment : BaseFragment() {

    private var _binding: FragmentDetailMovieBinding? = null
    private val binding get() = _binding!!

    private val viewModel by activityViewModels<MoviesViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailMovieBinding.inflate(inflater, container, false)
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
            collapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT)
            movieDetailToolbar.setNavigationOnClickListener { handleOnBackPress() }
        }
    }

    /**
     * Observe the [viewModel] events.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun collectViewModel() = launchAndRepeatOnLifecycle(state = Lifecycle.State.STARTED) {
        observeFor(viewModel.detailedData, ::movieCollector)
        observeFor(viewModel.movieTrailer, ::trailerCollector)
    }

    /**
     * Observe [movieData] pair of values from view model.
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun movieCollector(movieData: Pair<String?, MovieGenre?>) {
        val (imageUrl, movie) = movieData

        setGenres(movie?.genres)
        movie?.movie?.let {
            binding.collapsingToolbar.title = it.title

            binding.tvTitle.text = it.title

            it.releaseDate?.let { date ->
                binding.dcvYear.visibility = View.VISIBLE
                binding.dcvYear.setText(date.getYearFromDate().toString())
            }

            binding.dcvLang.setText(it.originalLanguage)

            binding.dcvRate.showIcon()
            binding.dcvRate.setText(it.voteAverage.toString())

            binding.detailBodySummary.text = it.overview

            Glide.with(requireContext())
                .load(imageUrl + it.posterPath)
                .error(R.drawable.ic_no_image)
                .into(binding.movieDetailPoster)

        }
    }

    private fun trailerCollector(trailer: Video?) {
        with(binding){
            if(trailer != null){
                btnWatchTrailer.visibility = View.VISIBLE
                btnWatchTrailer.setOnClickListener { openYotubeVideo(trailer.key) }
            }
            else{
                btnWatchTrailer.visibility = View.GONE
            }
        }
    }

    private fun setGenres(genres: List<Genre>?) {
        genres?.let {
            with(binding.llGenres) {
                removeAllViews()

                val margin = resources.getDimension(R.dimen.margin_4x).toInt()

                val textLayoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    marginEnd = margin
                    marginStart = margin
                }

                it.forEach {
                    val view = TextView(safeActivity).apply {
                        text = it.name
                        layoutParams = textLayoutParams
                    }
                    TextViewCompat.setTextAppearance(view, R.style.TextGenre)
                    this.addView(view)
                }
            }
        }
    }

    /**
     * Method for open Youtube to watch the trailer
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun openYotubeVideo(videoKey : String){
        val intent = Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube://${videoKey}")).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK
        }
        safeActivity.startActivity(intent)
    }

    /**
     * Method for navigate back
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    private fun handleOnBackPress() {
        findNavController().popBackStack()
    }

}