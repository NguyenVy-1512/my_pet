package com.nhat.remote

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nhat.data.model.MovieEntity
import com.nhat.remote.factory.MovieFactory
import com.nhat.remote.mapper.MovieEntityMapper
import com.nhat.remote.model.RemoteMovieModel
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieRemoteImplTest {

    private lateinit var entityMapper: MovieEntityMapper
    private lateinit var movieService: MovieService

    private lateinit var movieRemoteImpl: MovieRemoteImpl

    @Before
    fun setup() {
        entityMapper = mock()
        movieService = mock()
        movieRemoteImpl = MovieRemoteImpl(movieService, entityMapper)
    }

    //<editor-fold desc="Get Movies">
    @Test
    fun getMoviesCompletes() {
        stubMovieServiceGetMovies(Flowable.just(MovieFactory.makeMoviesListResponse()))
        val testObserver = movieRemoteImpl.getMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val response = MovieFactory.makeMoviesListResponse()
        stubMovieServiceGetMovies(Flowable.just(response))
        val entities = mutableListOf<MovieEntity>()
        response.results.forEach {
            entities.add(entityMapper.mapFromRemote(it))
        }

        val testObserver = movieRemoteImpl.getMovies().test()
        testObserver.assertValue(entities)
    }
    //</editor-fold>

    private fun stubMovieServiceGetMovies(
        observable:
        Flowable<ResponseList<RemoteMovieModel>>
    ) {
        whenever(movieService.getMovies())
            .thenReturn(observable)
    }
}