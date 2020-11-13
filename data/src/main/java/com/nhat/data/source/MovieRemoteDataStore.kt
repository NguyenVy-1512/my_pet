package com.nhat.data.source

import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieRemote
import com.nhat.data.repository.MovieDataStore
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

/**
 * Implementation of the [MovieDataStore] interface to provide a means of communicating
 * with the remote data source
 */
open class MovieRemoteDataStore @Inject constructor(private val IMovieRemote: IMovieRemote) :
    MovieDataStore {

    override fun clearMovies(): Completable {
        throw UnsupportedOperationException()
    }

    override fun saveMovies(movies: List<MovieEntity>): Completable {
        throw UnsupportedOperationException()
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the API
     */
    override fun getMovies(): Flowable<List<MovieEntity>> {
        return IMovieRemote.getMovies()
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the API
     */
    override fun searchMovies(query: String): Flowable<List<MovieEntity>> {
        return IMovieRemote.searchMovies(query)
    }


    /**
     * Retrieve a [MovieEntity] instance from the API
     */
    override fun getMovie(id: Long): Flowable<MovieEntity> {
        return IMovieRemote.getMovie(id)
    }

    override fun isCached(): Single<Boolean> {
        throw UnsupportedOperationException()
    }

}