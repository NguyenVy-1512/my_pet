package com.nhat.domain.usecase.movies

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import com.nhat.domain.executor.PostExecutionThread
import com.nhat.domain.executor.ThreadExecutor
import com.nhat.domain.interactor.movie.GetMovies
import com.nhat.domain.model.DomainMovieModel
import com.nhat.domain.repository.MovieRepository
import com.nhat.domain.test.factory.MovieFactory
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test

class GetMoviesTest {

    private lateinit var getMovies: GetMovies

    private lateinit var mockThreadExecutor: ThreadExecutor
    private lateinit var mockPostExecutionThread: PostExecutionThread
    private lateinit var mockMovieRepository: MovieRepository

    @Before
    fun setUp() {
        mockThreadExecutor = mock()
        mockPostExecutionThread = mock()
        mockMovieRepository = mock()
        getMovies = GetMovies(
            mockMovieRepository, mockThreadExecutor,
            mockPostExecutionThread
        )
    }

    @Test
    fun buildUseCaseObservableCallsRepository() {
        getMovies.buildUseCaseObservable(null)
        verify(mockMovieRepository).getMovies()
    }

    @Test
    fun buildUseCaseObservableCompletes() {
        stubMovieRepositoryGetMovies(Flowable.just(MovieFactory.makeMovieList(2)))
        val testObserver = getMovies.buildUseCaseObservable(null).test()
        testObserver.assertComplete()
    }

    @Test
    fun buildUseCaseObservableReturnsData() {
        val list = MovieFactory.makeMovieList(2)
        stubMovieRepositoryGetMovies(Flowable.just(list))
        val testObserver = getMovies.buildUseCaseObservable(null).test()
        testObserver.assertValue(list)
    }

    private fun stubMovieRepositoryGetMovies(single: Flowable<List<DomainMovieModel>>) {
        whenever(mockMovieRepository.getMovies())
            .thenReturn(single)
    }

}