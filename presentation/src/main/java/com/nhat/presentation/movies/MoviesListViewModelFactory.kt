package com.nhat.presentation.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nhat.domain.interactor.movie.GetMovies
import com.nhat.domain.interactor.movie.SearchMovies
import com.nhat.presentation.mapper.PresentationMovieMapper

@Suppress("UNCHECKED_CAST")
open class MoviesListViewModelFactory(
    private val getMovies: GetMovies,
    private val searchMovies: SearchMovies,
    private val presentationMovieMapper: PresentationMovieMapper
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MoviesListViewModel::class.java)) {
            return MoviesListViewModel(getMovies, searchMovies, presentationMovieMapper) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}