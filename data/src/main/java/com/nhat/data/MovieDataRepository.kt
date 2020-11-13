package com.nhat.data

import com.nhat.data.mapper.DataMovieMapper
import com.nhat.data.model.MovieEntity
import com.nhat.data.source.MovieDataStoreFactory
import com.nhat.domain.model.DomainMovieModel
import com.nhat.domain.repository.MovieRepository
import io.reactivex.Completable
import io.reactivex.Flowable
import javax.inject.Inject

/**
 * Provides an implementation of the [MovieRepository] interface for communicating to and from
 * data sources
 */
class MovieDataRepository @Inject constructor(
    private val factory: MovieDataStoreFactory,
    private val dataMovieMapper: DataMovieMapper
) :
    MovieRepository {

    override fun clearMovies(): Completable {
        return factory.retrieveCacheDataStore().clearMovies()
    }

    override fun saveMovies(domainMovieModels: List<DomainMovieModel>): Completable {
        val bufferooEntities = mutableListOf<MovieEntity>()
        domainMovieModels.map { bufferooEntities.add(dataMovieMapper.mapToEntity(it)) }
        return factory.retrieveCacheDataStore().saveMovies(bufferooEntities)
    }

    override fun getMovies(): Flowable<List<DomainMovieModel>> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher {
                factory.retrieveDataStore(it).getMovies()
            }
            .flatMap { list ->
                Flowable.just(list.map { dataMovieMapper.mapFromEntity(it) })
            }
            .flatMap {
                saveMovies(it).toSingle { it }.toFlowable()
            }
    }

    override fun searchMovies(query: String): Flowable<List<DomainMovieModel>> {
        return factory.retrieveRemoteDataStore().searchMovies(query)
            .flatMap { list ->
                Flowable.just(list.map { dataMovieMapper.mapFromEntity(it) })
            }
            .flatMap {
                saveMovies(it).toSingle { it }.toFlowable()
            }
    }

    override fun getMovie(id: Long): Flowable<DomainMovieModel> {
        return factory.retrieveCacheDataStore().isCached()
            .flatMapPublisher {
                factory.retrieveDataStore(it).getMovie(id)
            }
            .flatMap { Flowable.just(dataMovieMapper.mapFromEntity(it)) }
            .flatMap { saveMovies(listOf(it)).toSingle { it }.toFlowable() }
    }
}