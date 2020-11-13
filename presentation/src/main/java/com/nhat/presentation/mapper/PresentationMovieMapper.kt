package com.nhat.presentation.mapper

import com.nhat.domain.model.DomainMovieModel
import com.nhat.presentation.model.MovieView
import javax.inject.Inject

/**
 * Map a [MovieView] to and from a [DomainMovieModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class PresentationMovieMapper @Inject constructor() : Mapper<MovieView, DomainMovieModel> {
    /**
     * Map a [DomainMovieModel] instance to a [MovieView] instance
     */
    override fun mapToView(type: DomainMovieModel): MovieView {
        return MovieView(
            id = type.id,
            title = type.title,
            overView = type.overView,
            posterPath = type.posterPath,
            backDropPath = type.backDropPath,
            voteAverage = type.voteAverage
        )
    }
}