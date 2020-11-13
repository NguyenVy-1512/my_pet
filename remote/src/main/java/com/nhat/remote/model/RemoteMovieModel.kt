package com.nhat.remote.model

import com.google.gson.annotations.SerializedName

/**
 * Representation for a [RemoteMovieModel] fetched from the API
 */
class RemoteMovieModel(
    @SerializedName("id") val id: Long,
    @SerializedName("title") val title: String,
    @SerializedName("overview") val overView: String,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backDropPath: String?,
    @SerializedName("vote_average") val voteAverage: Double
)