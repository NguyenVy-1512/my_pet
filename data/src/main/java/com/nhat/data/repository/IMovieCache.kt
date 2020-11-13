package com.nhat.data.repository

import com.nhat.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the caching of Movies. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface IMovieCache {

    /**
     * Clear all Movies from the cache.
     */
    fun clearMovies(): Completable

    /**
     * Save a given list of Movies to the cache.
     */
    fun saveMovies(movies: List<MovieEntity>): Completable

    /**
     * Retrieve a list of Movies, from the cache.
     */
    fun getMovies(): Flowable<List<MovieEntity>>

    /**
     * Retrieve a Movies, from the cache.
     */
    fun getMovie(id: Long): Flowable<MovieEntity>

    /**
     * Check whether there is a list of Movies stored in the cache.
     *
     * @return true if the list is cached, otherwise false
     */
    fun isCached(): Single<Boolean>

    /**
     * Set a point in time at when the cache was last updated.
     *
     * @param lastCache the point in time at when the cache was last updated
     */
    fun setLastCacheTime(lastCache: Long)

    /**
     * Check if the cache is expired.
     *
     * @return true if the cache is expired, otherwise false
     */
    fun isExpired(): Boolean

}