package com.nhat.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nhat.data.factory.MovieFactory
import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieRemote
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IMovieRemoteDataStoreTest {

    private lateinit var movieRemoteDataStore: MovieRemoteDataStore

    private lateinit var iMovieRemote: IMovieRemote

    @Before
    fun setUp() {
        iMovieRemote = mock()
        movieRemoteDataStore = MovieRemoteDataStore(iMovieRemote)
    }

    //<editor-fold desc="Clear Movies">
    @Test(expected = UnsupportedOperationException::class)
    fun clearMoviesThrowsException() {
        movieRemoteDataStore.clearMovies().test()
    }
    //</editor-fold>

    //<editor-fold desc="Save Movies">
    @Test(expected = UnsupportedOperationException::class)
    fun saveMoviesThrowsException() {
        movieRemoteDataStore.saveMovies(MovieFactory.makeMovieEntityList(2)).test()
    }
    //</editor-fold>

    //<editor-fold desc="Get Movies">
    @Test
    fun getMoviesCompletes() {
        stubMovieCacheGetMovies(Flowable.just(MovieFactory.makeMovieEntityList(2)))
        val testObserver = iMovieRemote.getMovies().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubMovieCacheGetMovies(single: Flowable<List<MovieEntity>>) {
        whenever(iMovieRemote.getMovies())
            .thenReturn(single)
    }
    //</editor-fold>

}