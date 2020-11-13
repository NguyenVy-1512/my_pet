package com.nhat.domain.interactor.moviedetails

import com.nhat.domain.executor.PostExecutionThread
import com.nhat.domain.executor.ThreadExecutor
import com.nhat.domain.interactor.FlowableUseCase
import com.nhat.domain.model.DomainMovieModel
import com.nhat.domain.repository.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Use case used for retrieving a [List] of [DomainMovieModel] instances from the [MovieRepository]
 */
open class GetMovie @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<DomainMovieModel, GetMovie.Params>(threadExecutor, postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Flowable<DomainMovieModel> {
        requireNotNull(params) {
            "Params is required"
        }
        return movieRepository.getMovie(params.id)
    }

    data class Params(val id: Long)
}