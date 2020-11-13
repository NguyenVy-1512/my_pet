package com.nhat.cache

import com.nhat.cache.db.MoviesDatabase
import com.nhat.cache.mapper.MovieEntityMapper
import com.nhat.cache.model.CachedMovie
import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieCache
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Cached implementation for retrieving and saving DomainMovieModel instances. This class implements the
 * [IMovieCache] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class IMovieCacheImpl @Inject constructor(
    val moviesDatabase: MoviesDatabase,
    private val entityMapper: MovieEntityMapper,
    private val preferencesHelper: PreferencesHelper
) : IMovieCache {

    private val EXPIRATION_TIME = (60 * 10 * 1000).toLong()

    /**
     * Retrieve an instance from the database, used for tests.
     */
    internal fun getDatabase(): MoviesDatabase {
        return moviesDatabase
    }

    /**
     * Remove all the data from all the tables in the database.
     */
    override fun clearMovies(): Completable {
        return Completable.defer {
            moviesDatabase.cachedMovieDao().clearMovies()
            Completable.complete()
        }
    }

    /**
     * Save the given list of [MovieEntity] instances to the database.
     */
    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return Completable.defer {
            movies.forEach {
                moviesDatabase.cachedMovieDao().insertMovie(
                    entityMapper.mapToCached(it)
                )
            }
            Completable.complete()
        }
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the database.
     */
    override fun getMovies(): Flowable<List<MovieEntity>> {
        return Flowable.defer {
            Flowable.just(moviesDatabase.cachedMovieDao().getMovies())
        }.map {
            it.map { entityMapper.mapFromCached(it) }
        }
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the database.
     */
    override fun getMovie(id: Long): Flowable<MovieEntity> {
        return Flowable.defer {
            Flowable.just(moviesDatabase.cachedMovieDao().getMovie(id))
        }.map { entityMapper.mapFromCached(it) }
    }

    /**
     * Check whether there are instances of [CachedMovie] stored in the cache.
     */
    override fun isCached(): Single<Boolean> {
        return Single.defer {
            Single.just(moviesDatabase.cachedMovieDao().getMovies().isNotEmpty())
        }
    }

    /**
     * Set a point in time at when the cache was last updated.
     */
    override fun setLastCacheTime(lastCache: Long) {
        preferencesHelper.lastCacheTime = lastCache
    }

    /**
     * Check whether the current cached data exceeds the defined [EXPIRATION_TIME] time.
     */
    override fun isExpired(): Boolean {
        val currentTime = System.currentTimeMillis()
        val lastUpdateTime = this.getLastCacheUpdateTimeMillis()
        return currentTime - lastUpdateTime > EXPIRATION_TIME
    }

    /**
     * Get in millis, the last time the cache was accessed.
     */
    private fun getLastCacheUpdateTimeMillis(): Long {
        return preferencesHelper.lastCacheTime
    }

}