package com.nhat.data

import com.nhaarman.mockito_kotlin.*
import com.nhat.data.factory.MovieFactory
import com.nhat.data.mapper.DataMovieMapper
import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.MovieDataStore
import com.nhat.data.source.MovieCacheDataStore
import com.nhat.data.source.MovieDataStoreFactory
import com.nhat.data.source.MovieRemoteDataStore
import com.nhat.domain.model.DomainMovieModel
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class DomainMovieModelDataRepositoryTest {

    private lateinit var movieDataRepository: MovieDataRepository

    private lateinit var movieDataStoreFactory: MovieDataStoreFactory
    private lateinit var dataMovieMapper: DataMovieMapper
    private lateinit var movieCacheDataStore: MovieCacheDataStore
    private lateinit var movieRemoteDataStore: MovieRemoteDataStore

    @Before
    fun setUp() {
        movieDataStoreFactory = mock()
        dataMovieMapper = mock()
        movieCacheDataStore = mock()
        movieRemoteDataStore = mock()
        movieDataRepository = MovieDataRepository(movieDataStoreFactory, dataMovieMapper)
        stubMovieDataStoreFactoryRetrieveCacheDataStore()
        stubMovieDataStoreFactoryRetrieveRemoteDataStore()
    }

    //<editor-fold desc="Clear Movies">
    @Test
    fun clearMoviesCompletes() {
        stubMovieCacheClearMovies(Completable.complete())
        val testObserver = movieDataRepository.clearMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun clearMoviesCallsCacheDataStore() {
        stubMovieCacheClearMovies(Completable.complete())
        movieDataRepository.clearMovies().test()
        verify(movieCacheDataStore).clearMovies()
    }

    @Test
    fun clearMoviesNeverCallsRemoteDataStore() {
        stubMovieCacheClearMovies(Completable.complete())
        movieDataRepository.clearMovies().test()
        verify(movieRemoteDataStore, never()).clearMovies()
    }
    //</editor-fold>

    //<editor-fold desc="Save Movies">
    @Test
    fun saveMoviesCompletes() {
        stubMovieCacheSaveMovies(Completable.complete())
        val testObserver = movieDataRepository.saveMovies(
            MovieFactory.makeMovieList(2)
        ).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMoviesCallsCacheDataStore() {
        stubMovieCacheSaveMovies(Completable.complete())
        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieCacheDataStore).saveMovies(any())
    }

    @Test
    fun saveMoviesNeverCallsRemoteDataStore() {
        stubMovieCacheSaveMovies(Completable.complete())
        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any())
    }
    //</editor-fold>

    //<editor-fold desc="Get Movies">
    @Test
    fun getMoviesCompletes() {
        stubMovieCacheDataStoreIsCached(Single.just(true))
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheDataStoreGetMovies(
            Flowable.just(
                MovieFactory.makeMovieEntityList(2)
            )
        )
        stubMovieCacheSaveMovies(Completable.complete())
        val testObserver = movieDataRepository.getMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        stubMovieCacheDataStoreIsCached(Single.just(true))
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())
        val bufferoos = MovieFactory.makeMovieList(2)
        val bufferooEntities = MovieFactory.makeMovieEntityList(2)
        bufferoos.forEachIndexed { index, bufferoo ->
            stubMovieMapperMapFromEntity(bufferooEntities[index], bufferoo)
        }
        stubMovieCacheDataStoreGetMovies(Flowable.just(bufferooEntities))

        val testObserver = movieDataRepository.getMovies().test()
        testObserver.assertValue(bufferoos)
    }

    @Test
    fun getMoviesSavesMoviesWhenFromCacheDataStore() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieCacheDataStore)
        stubMovieCacheSaveMovies(Completable.complete())
        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieCacheDataStore).saveMovies(any())
    }

    @Test
    fun getMoviesNeverSavesMoviesWhenFromRemoteDataStore() {
        stubMovieDataStoreFactoryRetrieveDataStore(movieRemoteDataStore)
        stubMovieCacheSaveMovies(Completable.complete())
        movieDataRepository.saveMovies(MovieFactory.makeMovieList(2)).test()
        verify(movieRemoteDataStore, never()).saveMovies(any())
    }
    //</editor-fold>

    //<editor-fold desc="Stub helper methods">
    private fun stubMovieCacheSaveMovies(completable: Completable) {
        whenever(movieCacheDataStore.saveMovies(any()))
            .thenReturn(completable)
    }

    private fun stubMovieCacheDataStoreIsCached(single: Single<Boolean>) {
        whenever(movieCacheDataStore.isCached())
            .thenReturn(single)
    }

    private fun stubMovieCacheDataStoreGetMovies(single: Flowable<List<MovieEntity>>) {
        whenever(movieCacheDataStore.getMovies())
            .thenReturn(single)
    }

    private fun stubMovieRemoteDataStoreGetMovies(single: Flowable<List<MovieEntity>>) {
        whenever(movieRemoteDataStore.getMovies())
            .thenReturn(single)
    }

    private fun stubMovieCacheClearMovies(completable: Completable) {
        whenever(movieCacheDataStore.clearMovies())
            .thenReturn(completable)
    }

    private fun stubMovieDataStoreFactoryRetrieveCacheDataStore() {
        whenever(movieDataStoreFactory.retrieveCacheDataStore())
            .thenReturn(movieCacheDataStore)
    }

    private fun stubMovieDataStoreFactoryRetrieveRemoteDataStore() {
        whenever(movieDataStoreFactory.retrieveRemoteDataStore())
            .thenReturn(movieCacheDataStore)
    }

    private fun stubMovieDataStoreFactoryRetrieveDataStore(dataStore: MovieDataStore) {
        whenever(movieDataStoreFactory.retrieveDataStore(any()))
            .thenReturn(dataStore)
    }

    private fun stubMovieMapperMapFromEntity(
        movieEntity: MovieEntity,
        domainMovieModel: DomainMovieModel
    ) {
        whenever(dataMovieMapper.mapFromEntity(movieEntity))
            .thenReturn(domainMovieModel)
    }
    //</editor-fold>

}