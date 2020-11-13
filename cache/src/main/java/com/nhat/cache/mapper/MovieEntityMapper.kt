package com.nhat.cache.mapper

import com.nhat.cache.model.CachedMovie
import com.nhat.data.model.MovieEntity
import javax.inject.Inject

/**
 * Map a [CachedMovie] instance to and from a [MovieEntity] instance when data is moving between
 * this later and the Data layer
 */
open class MovieEntityMapper @Inject constructor() :
    EntityMapper<CachedMovie, MovieEntity> {

    /**
     * Map a [MovieEntity] instance to a [CachedMovie] instance
     */
    override fun mapToCached(type: MovieEntity): CachedMovie {
        return CachedMovie(
            id = type.id,
            title = type.title,
            overView = type.overView,
            posterPath = type.posterPath,
            backDropPath = type.backDropPath,
            voteAverage = type.voteAverage
        )
    }

    /**
     * Map a [CachedMovie] instance to a [MovieEntity] instance
     */
    override fun mapFromCached(type: CachedMovie): MovieEntity {
        return MovieEntity(
            id = type.id,
            title = type.title,
            overView = type.overView,
            posterPath = type.posterPath,
            backDropPath = type.backDropPath,
            voteAverage = type.voteAverage
        )
    }

}