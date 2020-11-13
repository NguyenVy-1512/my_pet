package com.nhat.domain.repository

import com.nhat.domain.model.DomainMovieModel
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Interface defining methods for how the data layer can pass data to and from the Domain layer.
 * This is to be implemented by the data layer, setting the requirements for the
 * operations that need to be implemented
 */
interface MovieRepository {

    fun clearMovies(): Completable

    fun saveMovies(domainMovieModels: List<DomainMovieModel>): Completable

    fun getMovies(): Flowable<List<DomainMovieModel>>

    fun searchMovies(query: String): Flowable<List<DomainMovieModel>>

    fun getMovie(id: Long): Flowable<DomainMovieModel>
}