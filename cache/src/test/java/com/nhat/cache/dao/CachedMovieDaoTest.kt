package com.nhat.cache.dao

import androidx.room.Room
import com.nhat.cache.db.MoviesDatabase
import com.nhat.cache.test.factory.MovieFactory
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
open class CachedMovieDaoTest {

    private lateinit var moviesDatabase: MoviesDatabase

    @Before
    fun initDb() {
        moviesDatabase = Room.inMemoryDatabaseBuilder(
            RuntimeEnvironment.application.baseContext,
            MoviesDatabase::class.java
        )
            .allowMainThreadQueries()
            .build()
    }

    @After
    fun closeDb() {
        moviesDatabase.close()
    }

    @Test
    fun insertMoviesSavesData() {
        val cachedMovie = MovieFactory.makeCachedMovie()
        moviesDatabase.cachedMovieDao().insertMovie(cachedMovie)

        val list = moviesDatabase.cachedMovieDao().getMovies()
        assert(list.isNotEmpty())
    }

    @Test
    fun getMoviesRetrievesData() {
        val cachedMovies = MovieFactory.makeCachedMovieList(5)

        cachedMovies.forEach {
            moviesDatabase.cachedMovieDao().insertMovie(it)
        }

        val retrievedMovies = moviesDatabase.cachedMovieDao().getMovies()
        assert(retrievedMovies == cachedMovies.sortedWith(compareBy({ it.id }, { it.id })))
    }

}