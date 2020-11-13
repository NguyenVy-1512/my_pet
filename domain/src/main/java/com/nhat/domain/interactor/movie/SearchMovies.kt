package com.nhat.domain.interactor.movie

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
open class SearchMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<DomainMovieModel>, SearchMovies.Params?>(
    threadExecutor,
    postExecutionThread
) {
    public override fun buildUseCaseObservable(params: Params?): Flowable<List<DomainMovieModel>> {
        requireNotNull(params)
        return movieRepository.searchMovies(params.query)
    }

    data class Params(val query: String)
}