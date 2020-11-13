package com.nhat.presentation.model

import android.content.Context
import com.nhat.presentation.R

/**
 * Representation for a [MovieView] instance for this layers Model representation
 */
class MovieView(
    val id: Long,
    val title: String,
    val overView: String,
    val posterPath: String?,
    val backDropPath: String?,
    val voteAverage: Double
) {
    fun voteAverageString(context: Context) =
        "${context.getString(R.string.vote_average)} : $voteAverage"
}