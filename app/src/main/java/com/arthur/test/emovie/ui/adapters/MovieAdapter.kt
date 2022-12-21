package com.arthur.test.emovie.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.arthur.test.emovie.R
import com.arthur.test.emovie.data.model.Movie
import com.arthur.test.emovie.databinding.MovieItemBinding
import com.bumptech.glide.Glide


class MovieAdapter(private val listener: ClickListenerAdapter<Movie>) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    private var imageUrl : String? = ""
    private val dataList : MutableList<Movie> = mutableListOf()

    /**
     * Set a list to this Adapter's [dataList]
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun setData(movieList: List<Movie>?){
        dataList.clear()
        movieList?.let{
            dataList.addAll(it)
        }
        notifyDataSetChanged()
    }

    /**
     * Set [url] value to [imageUrl]
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    fun setImageUrl(url: String?){
        imageUrl = url
    }

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     *
     * @author: Arturo Segura
     * @since: 1.0
     */
    inner class ViewHolder(val binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MovieItemBinding.inflate(inflater, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val movie = dataList[position]
        with(holder){
            val imgPath = imageUrl+movie.posterPath

            val drawable = CircularProgressDrawable(itemView.context).apply {
                setColorSchemeColors(
                    R.color.purple_500,
                    R.color.purple_700,
                    R.color.teal_200
                )
                centerRadius = 30f
                strokeWidth = 5f
            }
            drawable.start()

            Glide.with(itemView.context)
                .load(imgPath)
                .error(R.drawable.ic_no_image)
                .placeholder(drawable)
                .into(binding.movieImage)

            binding.movieItem.setOnClickListener {
                listener.onItemClicked(position, dataList[position])
            }
        }
    }

    override fun getItemCount(): Int = dataList.size

}