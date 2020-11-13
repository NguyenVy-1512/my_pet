package com.nhat.data.mapper

import com.nhat.data.model.MovieEntity
import com.nhat.domain.model.DomainMovieModel
import javax.inject.Inject


/**
 * Map a [MovieEntity] to and from a [DomainMovieModel] instance when data is moving between
 * this later and the Domain layer
 */
open class DataMovieMapper @Inject constructor() : Mapper<MovieEntity, DomainMovieModel> {

    /**
     * Map a [MovieEntity] instance to a [DomainMovieModel] instance
     */
    override fun mapFromEntity(type: MovieEntity): DomainMovieModel {
        return DomainMovieModel(
            id = type.id,
            title = type.title,
            overView = type.overView,
            posterPath = type.posterPath,
            backDropPath = type.backDropPath,
            voteAverage = type.voteAverage
        )
    }

    /**
     * Map a [DomainMovieModel] instance to a [MovieEntity] instance
     */
    override fun mapToEntity(type: DomainMovieModel): MovieEntity {
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