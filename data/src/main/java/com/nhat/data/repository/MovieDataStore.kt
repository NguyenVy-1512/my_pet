package com.nhat.data.repository

import com.nhat.data.model.MovieEntity
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Interface defining methods for the data operations related to Movies.
 * This is to be implemented by external data source layers, setting the requirements for the
 * operations that need to be implemented
 */
interface MovieDataStore {

    fun clearMovies(): Completable

    fun saveMovies(movies: List<MovieEntity>): Completable

    fun getMovies(): Flowable<List<MovieEntity>>

    fun searchMovies(query: String): Flowable<List<MovieEntity>>

    fun getMovie(id: Long): Flowable<MovieEntity>

    fun isCached(): Single<Boolean>

}