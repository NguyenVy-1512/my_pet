package com.nhat.data.source

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nhat.data.repository.IMovieCache
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class MovieDataStoreFactoryTest {

    private lateinit var movieDataStoreFactory: MovieDataStoreFactory

    private lateinit var iMovieCache: IMovieCache
    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieRemoteDataStore: MovieRemoteDataStore

    @Before
    fun setUp() {
        iMovieCache = mock()
        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()
        movieDataStoreFactory = MovieDataStoreFactory(
            iMovieCache,
            movieCacheDataStore, movieRemoteDataStore
        )
    }

    //<editor-fold desc="Retrieve Data Store">
    @Test
    fun retrieveDataStoreWhenNotCachedReturnsRemoteDataStore() {
        stubMovieCacheIsCached(Single.just(false))
        val movieDataStore = movieDataStoreFactory.retrieveDataStore(false)
        assert(movieDataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreWhenCacheExpiredReturnsRemoteDataStore() {
        stubMovieCacheIsCached(Single.just(true))
        stubMovieCacheIsExpired(true)
        val movieDataStore = movieDataStoreFactory.retrieveDataStore(true)
        assert(movieDataStore is MovieRemoteDataStore)
    }

    @Test
    fun retrieveDataStoreReturnsCacheDataStore() {
        stubMovieCacheIsCached(Single.just(true))
        stubMovieCacheIsExpired(false)
        val movieDataStore = movieDataStoreFactory.retrieveDataStore(true)
        assert(movieDataStore is MovieCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Remote Data Store">
    @Test
    fun retrieveRemoteDataStoreReturnsRemoteDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveRemoteDataStore()
        assert(movieDataStore is MovieRemoteDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Retrieve Cache Data Store">
    @Test
    fun retrieveCacheDataStoreReturnsCacheDataStore() {
        val movieDataStore = movieDataStoreFactory.retrieveCacheDataStore()
        assert(movieDataStore is MovieCacheDataStore)
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubMovieCacheIsCached(single: Single<Boolean>) {
        whenever(iMovieCache.isCached())
            .thenReturn(single)
    }

    private fun stubMovieCacheIsExpired(isExpired: Boolean) {
        whenever(iMovieCache.isExpired())
            .thenReturn(isExpired)
    }
    //</editor-fold>

}