package com.nhat.boiterplate.mapper

import com.nhat.boiterplate.model.MovieViewModel
import com.nhat.presentation.model.MovieView
import javax.inject.Inject

/**
 * Map a [MovieView] to and from a [MovieViewModel] instance when data is moving between
 * this layer and the Domain layer
 */
open class AppMovieMapper @Inject constructor() : Mapper<MovieViewModel, MovieView> {

    /**
     * Map a [MovieView] instance to a [MovieViewModel] instance
     */
    override fun mapToViewModel(type: MovieView): MovieViewModel {
        return MovieViewModel(
            id = type.id,
            title = type.title,
            overView = type.overView,
            posterPath = type.posterPath,
            backDropPath = type.backDropPath,
            voteAverage = type.voteAverage
        )
    }

}