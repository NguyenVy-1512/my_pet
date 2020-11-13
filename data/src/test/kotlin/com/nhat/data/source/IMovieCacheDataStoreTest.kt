package com.nhat.data.source

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nhat.data.factory.MovieFactory
import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieCache
import io.reactivex.Completable
import io.reactivex.Flowable
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class IMovieCacheDataStoreTest {

    private lateinit var movieCacheDataStore: MovieCacheDataStore

    private lateinit var iMovieCache: IMovieCache

    @Before
    fun setUp() {
        iMovieCache = mock()
        movieCacheDataStore = MovieCacheDataStore(iMovieCache)
    }

    //<editor-fold desc="Clear Movies">
    @Test
    fun clearMoviesCompletes() {
        stubMovieCacheClearMovies(Completable.complete())
        val testObserver = movieCacheDataStore.clearMovies().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Save Movies">
    @Test
    fun saveMoviesCompletes() {
        stubMovieCacheSaveMovies(Completable.complete())
        val testObserver = movieCacheDataStore.saveMovies(
            MovieFactory.makeMovieEntityList(2)
        ).test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Get Movies">
    @Test
    fun getMoviesCompletes() {
        stubMovieCacheGetMovies(Flowable.just(MovieFactory.makeMovieEntityList(2)))
        val testObserver = movieCacheDataStore.getMovies().test()
        testObserver.assertComplete()
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubMovieCacheSaveMovies(completable: Completable) {
        whenever(iMovieCache.saveMovies(any()))
            .thenReturn(completable)
    }

    private fun stubMovieCacheGetMovies(single: Flowable<List<MovieEntity>>) {
        whenever(iMovieCache.getMovies())
            .thenReturn(single)
    }

    private fun stubMovieCacheClearMovies(completable: Completable) {
        whenever(iMovieCache.clearMovies())
            .thenReturn(completable)
    }
    //</editor-fold>

}