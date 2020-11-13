package com.nhat.data.model

/**
 * Representation for a [MovieEntity] fetched from an external layer data source
 */
data class MovieEntity(
    val id: Long,
    val title: String,
    val overView: String,
    val posterPath: String?,
    val backDropPath: String?,
    val voteAverage: Double
)