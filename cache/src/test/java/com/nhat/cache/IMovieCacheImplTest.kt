package com.nhat.cache

import androidx.room.Room
import com.nhat.cache.db.MoviesDatabase
import com.nhat.cache.mapper.MovieEntityMapper
import com.nhat.cache.model.CachedMovie
import com.nhat.cache.test.factory.MovieFactory
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config
import kotlin.test.assertEquals

@RunWith(RobolectricTestRunner::class)
@Config(sdk = intArrayOf(21))
class IMovieCacheImplTest {

    private var moviesDatabase = Room.inMemoryDatabaseBuilder(
        RuntimeEnvironment.application,
        MoviesDatabase::class.java
    ).allowMainThreadQueries().build()
    private var entityMapper = MovieEntityMapper()
    private var preferencesHelper = PreferencesHelper(RuntimeEnvironment.application)


    private val databaseHelper = IMovieCacheImpl(
        moviesDatabase,
        entityMapper, preferencesHelper
    )

    @Test
    fun clearTablesCompletes() {
        val testObserver = databaseHelper.clearMovies().test()
        testObserver.assertComplete()
    }

    //<editor-fold desc="Save Movies">
    @Test
    fun saveMoviesCompletes() {
        val entities = MovieFactory.makeMovieEntityList(2)

        val testObserver = databaseHelper.saveMovies(entities).test()
        testObserver.assertComplete()
    }

    @Test
    fun saveMoviesSavesData() {
        val count = 2
        val entities = MovieFactory.makeMovieEntityList(count)

        databaseHelper.saveMovies(entities).test()
        checkNumRowsInMoviesTable(count)
    }
    //</editor-fold>

    //<editor-fold desc="Get Movies">
    @Test
    fun getMoviesCompletes() {
        val testObserver = databaseHelper.getMovies().test()
        testObserver.assertComplete()
    }

    @Test
    fun getMoviesReturnsData() {
        val entities = MovieFactory.makeMovieEntityList(2)
        val cachedMovies = mutableListOf<CachedMovie>()
        entities.forEach {
            cachedMovies.add(entityMapper.mapToCached(it))
        }
        insertMovies(cachedMovies)

        databaseHelper.getMovies().test()
    }
    //</editor-fold>

    private fun insertMovies(cachedMovies: List<CachedMovie>) {
        cachedMovies.forEach {
            moviesDatabase.cachedMovieDao().insertMovie(it)
        }
    }

    private fun checkNumRowsInMoviesTable(expectedRows: Int) {
        val numberOfRows = moviesDatabase.cachedMovieDao().getMovies().size
        assertEquals(expectedRows, numberOfRows)
    }

}