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
open class GetMovies @Inject constructor(
    private val movieRepository: MovieRepository,
    threadExecutor: ThreadExecutor,
    postExecutionThread: PostExecutionThread
) : FlowableUseCase<List<DomainMovieModel>, Void?>(threadExecutor, postExecutionThread) {
    public override fun buildUseCaseObservable(params: Void?): Flowable<List<DomainMovieModel>> {
        return movieRepository.getMovies()
    }
}