package com.nhat.presentation.movie

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.nhat.domain.interactor.moviedetails.GetMovie
import com.nhat.domain.model.DomainMovieModel
import com.nhat.presentation.data.Resource
import com.nhat.presentation.data.ResourceState
import com.nhat.presentation.mapper.PresentationMovieMapper
import com.nhat.presentation.model.MovieView
import io.reactivex.subscribers.DisposableSubscriber
import javax.inject.Inject

open class MovieDetailsViewModel @Inject constructor(
    private val getMovie: GetMovie,
    private val presentationMovieMapper: PresentationMovieMapper
) : ViewModel() {
    private val liveData: MutableLiveData<Resource<MovieView>> = MutableLiveData()

    fun get(): LiveData<Resource<MovieView>> {
        return liveData
    }

    fun fetch(id: Long) {
        liveData.postValue(Resource(ResourceState.LOADING, null, null))
        return getMovie.execute(MovieSubscriber(), GetMovie.Params(id))
    }

    override fun onCleared() {
        getMovie.dispose()
        super.onCleared()
    }

    inner class MovieSubscriber : DisposableSubscriber<DomainMovieModel>() {

        override fun onComplete() {}

        override fun onNext(domainMovieModel: DomainMovieModel) {
            liveData.postValue(
                Resource(
                    ResourceState.SUCCESS,
                    presentationMovieMapper.mapToView(domainMovieModel),
                    null
                )
            )
        }

        override fun onError(exception: Throwable) {
            liveData.postValue(Resource(ResourceState.ERROR, null, exception.message))
        }
    }
}
