package com.nhat.boiterplate.ui.movie

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.nhat.boiterplate.R
import com.nhat.boiterplate.common.BaseFragment
import com.nhat.boiterplate.mapper.AppMovieMapper
import com.nhat.boiterplate.model.MovieViewModel
import com.nhat.presentation.model.MovieView
import com.nhat.presentation.movie.MovieDetailsViewModel
import kotlinx.android.synthetic.main.movie_details_fragment.*
import javax.inject.Inject

class MovieDetailsFragment : BaseFragment<MovieView>() {
    override val layoutId: Int
        get() = R.layout.movie_details_fragment

    @Inject
    lateinit var mapper: AppMovieMapper

    private lateinit var viewModel: MovieDetailsViewModel

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(MovieDetailsViewModel::class.java)

        val movie = arguments?.get("movie") as MovieViewModel

        activity?.findViewById<Toolbar>(R.id.appBar)?.let {
            it.title = movie.title
            it.setNavigationIcon(R.drawable.ic_arrow_back_white_24dp)
            it.setNavigationOnClickListener {
                activity?.onBackPressed()
            }
        }

        bindViewWithModel(movie)

        viewModel.get()
            .observe(this, Observer { this.handleDataState(it.status, it.data, it.message) })
        viewModel.fetch(movie.id)
    }

    private fun bindViewWithModel(movie: MovieViewModel) {
        Glide.with(context!!)
            .load(movie.backDropPathUrl)
            .placeholder(R.drawable.placeholder16x9)
            .error(R.drawable.placeholder16x9)
            .into(imv_backDrop)
        tv_movieName.text = movie.title
        tv_movieOverView.text = movie.overView
    }

    override fun setupScreenForLoadingState() {
        //Do nothing
    }

    override fun setupScreenForSuccess(t: MovieView?) {
        t?.let {
            val viewModel = mapper.mapToViewModel(t)
            bindViewWithModel(viewModel)
        }
    }

    override fun setupScreenForError(message: String?) {
        context?.let {
            Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
        }
    }
}
