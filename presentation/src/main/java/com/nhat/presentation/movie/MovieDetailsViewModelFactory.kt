package com.nhat.presentation.movie

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhat.domain.interactor.moviedetails.GetMovie
import com.nhat.presentation.mapper.PresentationMovieMapper

@Suppress("UNCHECKED_CAST")
open class MovieDetailsViewModelFactory(
    private val getMovie: GetMovie,
    private val presentationMovieMapper: PresentationMovieMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MovieDetailsViewModel::class.java)) {
            return MovieDetailsViewModel(getMovie, presentationMovieMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}