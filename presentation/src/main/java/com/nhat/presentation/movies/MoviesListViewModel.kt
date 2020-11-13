package com.nhat.presentation.movies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhat.domain.interactor.movie.GetMovies
import com.nhat.domain.interactor.movie.SearchMovies
import com.nhat.domain.model.DomainMovieModel
import com.nhat.presentation.data.Resource
import com.nhat.presentation.data.ResourceState
import com.nhat.presentation.mapper.PresentationMovieMapper
import com.nhat.presentation.model.MovieView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class MoviesListViewModel @Inject internal constructor(
    private val getMovies: GetMovies,
    private val searchMovies: SearchMovies,
    private val presentationMovieMapper: PresentationMovieMapper
) : ViewModel() {

    private val liveData: MutableLiveData<Resource<List<MovieView>>> = MutableLiveData()

    init {
        fetch()
    }

    fun getLiveDataValue(): List<MovieView> = liveData.value?.data ?: listOf()

    override fun onCleared() {
        getMovies.dispose()
        super.onCleared()
    }

    fun get(): LiveData<Resource<List<MovieView>>> {
        return liveData
    }

    fun fetch() {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getMovies.execute(MoviesListSubscriber())
    }

    fun search(query: String) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return searchMovies.execute(MoviesListSubscriber(), SearchMovies.Params(query))
    }

    inner class MoviesListSubscriber : DisposableSubscriber<List<DomainMovieModel>>() {

        override fun onComplete() { }

        override fun onNext(t: List<DomainMovieModel>) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    t.map { presentationMovieMapper.mapToView(it) }, null
                )
            )
        }

        override fun onError(exception: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}