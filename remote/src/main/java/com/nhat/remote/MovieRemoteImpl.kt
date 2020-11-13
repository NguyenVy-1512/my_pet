package com.nhat.remote

import com.nhat.data.model.MovieEntity
import com.nhat.data.repository.IMovieRemote
import com.nhat.remote.mapper.MovieEntityMapper
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Remote implementation for retrieving DomainMovieModel instances. This class implements the
 * [IMovieRemote] from the Data layer as it is that layers responsibility for defining the
 * operations in which data store implementation layers can carry out.
 */
class MovieRemoteImpl @Inject constructor(
    private val movieService: MovieService,
    private val entityMapper: MovieEntityMapper
) : IMovieRemote {

    /**
     * Retrieve a list of [MovieEntity] instances from the [MovieService].
     */
    override fun getMovies(): Flowable<List<MovieEntity>> {
        return movieService.getMovies()
            .map { it.results }
            .map {
                val entities = mutableListOf<MovieEntity>()
                it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                entities
            }
    }

    /**
     * Retrieve a list of [MovieEntity] instances from the [MovieService].
     */
    override fun searchMovies(query: String): Flowable<List<MovieEntity>> {
        return movieService.searchMovies(query = query)
            .map { it.results }
            .map {
                val entities = mutableListOf<MovieEntity>()
                it.forEach { entities.add(entityMapper.mapFromRemote(it)) }
                entities
            }
    }


    /**
     * Retrieve a  [MovieEntity] instance from the [MovieService].
     */
    override fun getMovie(id: Long): Flowable<MovieEntity> =
        movieService.getMovie(id = id).map { entityMapper.mapFromRemote(it) }
}