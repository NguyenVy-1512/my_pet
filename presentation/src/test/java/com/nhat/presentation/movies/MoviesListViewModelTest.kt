package com.nhat.presentation.movies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.nhaarman.mockito_kotlin.*
import com.nhat.domain.interactor.movie.GetMovies
import com.nhat.domain.interactor.movie.SearchMovies
import com.nhat.domain.model.DomainMovieModel
import com.nhat.presentation.data.ResourceState
import com.nhat.presentation.factory.DataFactory
import com.nhat.presentation.factory.MovieFactory
import com.nhat.presentation.mapper.PresentationMovieMapper
import com.nhat.presentation.model.MovieView
import io.reactivex.subscribers.DisposableSubscriber
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Captor
import org.mockito.Mock

@RunWith(JUnit4::class)
class MoviesListViewModelTest {

    @get:Rule var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Mock
    lateinit var getMovies: GetMovies

    @Mock
    lateinit var searchMovies: SearchMovies

    @Mock
    lateinit var presentationMovieMapper: PresentationMovieMapper

    @Captor
    private lateinit var captor: KArgumentCaptor<DisposableSubscriber<List<DomainMovieModel>>>

    private lateinit var moviesListViewModel: MoviesListViewModel

    @Before
    fun setUp() {
        captor = argumentCaptor<DisposableSubscriber<List<DomainMovieModel>>>()
        getMovies = mock()
        searchMovies = mock()
        presentationMovieMapper = mock()
        moviesListViewModel = MoviesListViewModel(getMovies, searchMovies, presentationMovieMapper)
    }

    @Test
    fun getMoviesExecutesUseCase() {
        moviesListViewModel.get()

        verify(getMovies, times(1)).execute(any(), anyOrNull())
    }

    //<editor-fold desc="Success">
    @Test
    fun getMoviesReturnsSuccess() {
        val list = MovieFactory.makeMovieList(2)
        val viewList = MovieFactory.makeMovieViewList(2)
        stubMovieMapperMapToView(viewList[0], list[0])
        stubMovieMapperMapToView(viewList[1], list[1])

        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(moviesListViewModel.get().value?.status == ResourceState.SUCCESS)
    }

    @Test
    fun getMoviesReturnsDataOnSuccess() {
        val list = MovieFactory.makeMovieList(2)
        val viewList = MovieFactory.makeMovieViewList(2)

        stubMovieMapperMapToView(viewList[0], list[0])
        stubMovieMapperMapToView(viewList[1], list[1])

        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(moviesListViewModel.get().value?.data == viewList)
    }

    @Test
    fun getMoviesReturnsNoMessageOnSuccess() {
        val list = MovieFactory.makeMovieList(2)
        val viewList = MovieFactory.makeMovieViewList(2)

        stubMovieMapperMapToView(viewList[0], list[0])
        stubMovieMapperMapToView(viewList[1], list[1])

        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onNext(list)

        assert(moviesListViewModel.get().value?.message == null)
    }
    //</editor-fold>

    //<editor-fold desc="Error">
    @Test
    fun getMoviesReturnsError() {
        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(moviesListViewModel.get().value?.status == ResourceState.ERROR)
    }

    @Test
    fun getMoviesFailsAndContainsNoData() {
        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException())

        assert(moviesListViewModel.get().value?.data == null)
    }

    @Test
    fun getMoviesFailsAndContainsMessage() {
        val errorMessage = DataFactory.randomUuid()
        moviesListViewModel.get()

        verify(getMovies).execute(captor.capture(), eq(null))
        captor.firstValue.onError(RuntimeException(errorMessage))

        assert(moviesListViewModel.get().value?.message == errorMessage)
    }
    //</editor-fold>

    //<editor-fold desc="Loading">
    @Test
    fun getMoviesReturnsLoading() {
        moviesListViewModel.get()

        assert(moviesListViewModel.get().value?.status == ResourceState.LOADING)
    }

    @Test
    fun getMoviesContainsNoDataWhenLoading() {
        moviesListViewModel.get()

        assert(moviesListViewModel.get().value?.data == null)
    }

    @Test
    fun getMoviesContainsNoMessageWhenLoading() {
        moviesListViewModel.get()

        assert(moviesListViewModel.get().value?.data == null)
    }
    //</editor-fold>

    private fun stubMovieMapperMapToView(
        movieView: MovieView,
        domainMovieModel: DomainMovieModel
    ) {
        whenever(presentationMovieMapper.mapToView(domainMovieModel)).doReturn(movieView)
    }

}