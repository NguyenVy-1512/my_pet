package com.nhat.domain.model

/**
 * Representation for a [DomainMovieModel] fetched from an external layer data source
 */
data class DomainMovieModel(
    val id: Long,
    val title: String,
    val overView: String,
    val posterPath: String?,
    val backDropPath: String?,
    val voteAverage: Double
)