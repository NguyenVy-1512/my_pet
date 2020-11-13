package com.nhat.boiterplate.model

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.android.parcel.IgnoredOnParcel
import kotlinx.android.parcel.Parcelize

/**
 * Representation for a [MovieViewModel] fetched from an external layer data source
 */
@Keep
@Parcelize
class MovieViewModel(
    val id: Long,
    val title: String,
    val overView: String,
    val posterPath: String?,
    val backDropPath: String?,
    val voteAverage: Double
) : Parcelable {
    @IgnoredOnParcel
    val posterPathUrl = "$MovieImagesPrefix$posterPath"
    @IgnoredOnParcel
    val backDropPathUrl = "$MovieImagesPrefix$backDropPath"

    companion object {
        const val MovieImagesPrefix = "https://image.tmdb.org/t/p/original/"
    }
}