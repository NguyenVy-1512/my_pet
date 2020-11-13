package com.nhat.remote.mapper

import com.nhat.data.model.MovieEntity
import com.nhat.remote.model.RemoteMovieModel
import javax.inject.Inject

/**
 * Map a [RemoteMovieModel] to and from a [MovieEntity] instance when data is moving between
 * this later and the Data layer
 */
open class MovieEntityMapper @Inject constructor() :
    EntityMapper<RemoteMovieModel, MovieEntity> {

    /**
     * Map an instance of a [RemoteMovieModel] to a [MovieEntity] model
     */
    override fun mapFromRemote(type: RemoteMovieModel): MovieEntity {
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