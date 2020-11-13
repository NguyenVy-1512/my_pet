package com.nhat.data.source

import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieCache
import com.nhat.data.repository.MovieDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [MovieDataStore] interface to provide a means of communicating
 * with the local data source
 */
open class MovieCacheDataStore @Inject constructor(private val IMovieCache: IMovieCache) :
    MovieDataStore {

    /**
     * Clear all Movies from the cache
     */
    override fun clearMovies(): Completable {
        return IMovieCache.clearMovies()
    }

    /**
     * Save a given [List] of [MovieEntity] instances to the cache
     */
    override fun saveMovies(movies: List<MovieEntity>): Completable {
        return IMovieCache.saveMovies(movies)
            .doOnComplete {
                IMovieCache.setLastCacheTime(System.currentTimeMillis())
            }
    }

    /**
     * Retrieve a list of [MovieEntity] instance from the cache
     */
    override fun getMovies(): Flowable<List<MovieEntity>> {
        return IMovieCache.getMovies()
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the API
     */
    override fun searchMovies(query: String): Flowable<List<MovieEntity>> {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a [MovieEntity] instance from the cache
     */
    override fun getMovie(id: Long): Flowable<MovieEntity> {
        return IMovieCache.getMovie(id)
    }

    /**
     * Retrieve a list of [MovieEntity] instance from the cache
     */
    override fun isCached(): Single<Boolean> {
        return IMovieCache.isCached()
    }

}