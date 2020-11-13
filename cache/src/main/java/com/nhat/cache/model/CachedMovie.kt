package com.nhat.cache.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nhat.cache.db.constants.MovieConstants

/**
 * Model used solely for the caching of a movie
 */
@Entity(tableName = MovieConstants.TABLE_NAME)
data class CachedMovie(
    @PrimaryKey
    var id: Long,
    val title: String,
    val overView: String,
    val posterPath: String?,
    val backDropPath: String?,
    val voteAverage: Double
)