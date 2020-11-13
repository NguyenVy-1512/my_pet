package com.nhat.boiterplate.ui.movies


import android.os.Bundle
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.jakewharton.rxbinding3.widget.queryTextChanges
import com.nhat.boiterplate.R
import com.nhat.boiterplate.common.BaseFragment
import com.nhat.boiterplate.mapper.AppMovieMapper
import com.nhat.boiterplate.widget.empty.EmptyListener
import com.nhat.boiterplate.widget.error.ErrorListener
import com.nhat.presentation.model.MovieView
import com.nhat.presentation.movies.MoviesListViewModel
import com.yarolegovich.discretescrollview.InfiniteScrollAdapter
import com.yarolegovich.discretescrollview.transform.ScaleTransformer
import kotlinx.android.synthetic.main.moves_list_fragment.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class MoviesListFragment : BaseFragment<List<MovieView>>() {
    override val layoutId: Int = R.layout.moves_list_fragment
    @Inject
    lateinit var moviesListAdapter: MoviesListAdapter
    @Inject
    lateinit var mapper: AppMovieMapper
    private lateinit var moviesListViewModel: MoviesListViewModel
    private lateinit var wrapper: InfiniteScrollAdapter<MoviesListAdapter.ViewHolder>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        activity?.findViewById<Toolbar>(R.id.appBar)?.let {
            it.navigationIcon = null
            it.setNavigationOnClickListener(null)
        }

        getSupportToolbar()?.setTitle(R.string.tiki_exercise)

        moviesListViewModel = ViewModelProviders.of(activity!!, viewModelFactory)
            .get(MoviesListViewModel::class.java)

        moviesListViewModel.get().observe(this, Observer {
            if (it != null) this.handleDataState(it.status, it.data, it.message)
        })

        setupBrowseRecycler()
        setupViewListeners()
    }

    override fun onStart() {
        super.onStart()

        disposable.add(moviesListAdapter.itemEventSource.subscribe {
            if (findNavController().currentDestination?.id == R.id.movieListFragment) {
                val positionInDataSet = this.wrapper.getRealPosition(it)
                val data = moviesListViewModel.getLiveDataValue()[positionInDataSet]
                findNavController().navigate(
                    MoviesListFragmentDirections.actionMovieListFragmentToMovieDetailsFragment(
                        mapper.mapToViewModel(data)
                    )
                )
            }
        })
    }

    private fun setupBrowseRecycler() {
        recycler_browse.setItemTransformer(
            ScaleTransformer.Builder()
                .setMinScale(0.8f)
                .build()
        )
        recycler_browse.setSlideOnFling(true)

        wrapper = InfiniteScrollAdapter.wrap<MoviesListAdapter.ViewHolder>(moviesListAdapter)

        recycler_browse.adapter = wrapper
        recycler_browse.addOnItemChangedListener { _, i ->
            val positionInDataSet = wrapper.getRealPosition(i)
            val data = moviesListViewModel.getLiveDataValue()[positionInDataSet]
            tv_movieName.text = data.title
            tv_voteAverage.text = data.voteAverageString(tv_voteAverage.context)
        }
    }

    override fun setupScreenForLoadingState() {
        progress.visibility = View.VISIBLE
        moviesContainer.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.GONE
    }

    override fun setupScreenForSuccess(t: List<MovieView>?) {
        view_error.visibility = View.GONE
        progress.visibility = View.GONE
        if (t != null && t.isNotEmpty()) {
            updateListView(t)
            moviesContainer.visibility = View.VISIBLE
        } else {
            view_empty.visibility = View.VISIBLE
        }
    }

    private fun updateListView(movies: List<MovieView>) {
        moviesListAdapter.movies.submitList(movies.map { mapper.mapToViewModel(it) })
    }

    override fun setupScreenForError(message: String?) {
        progress.visibility = View.GONE
        moviesContainer.visibility = View.GONE
        view_empty.visibility = View.GONE
        view_error.visibility = View.VISIBLE
    }

    private fun setupViewListeners() {
        view_empty.emptyListener = emptyListener
        view_error.errorListener = errorListener
        disposable.addAll(
            searchView.queryTextChanges().debounce(
                300,
                TimeUnit.MILLISECONDS
            ).filter { it.isNotBlank() }.subscribe {
                moviesListViewModel.search(it.toString())
            }
        )
    }

    private val emptyListener = object : EmptyListener {
        override fun onCheckAgainClicked() {
            moviesListViewModel.fetch()
        }
    }

    private val errorListener = object : ErrorListener {
        override fun onTryAgainClicked() {
            moviesListViewModel.fetch()
        }
    }
}
