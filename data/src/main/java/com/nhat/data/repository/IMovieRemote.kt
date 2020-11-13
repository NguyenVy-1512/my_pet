package com.nhat.data.repository

import com.nhat.data.model.MovieEntity
import io.reactivex.Flowable

/**
 * Interface defining methods for the caching of Movies. This is to be implemented by the
 * cache layer, using this interface as a way of communicating.
 */
interface IMovieRemote {

    /**
     * Retrieve a list of Movies, from the cache
     */
    fun getMovies(): Flowable<List<MovieEntity>>

    /**
     * Retrieve a list of Movies, from the cache
     */
    fun searchMovies(query: String): Flowable<List<MovieEntity>>

    /**
     * Retrieve a Movie, from the cache
     */
    fun getMovie(id: Long): Flowable<MovieEntity>
}