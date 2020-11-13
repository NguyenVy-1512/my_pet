package com.nhat.cache.mapper

import com.nhat.cache.model.CachedMovie
import com.nhat.cache.test.factory.MovieFactory
import com.nhat.data.model.MovieEntity
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class MovieEntityMapperTest {

    private lateinit var movieEntityMapper: MovieEntityMapper

    @Before
    fun setUp() {
        movieEntityMapper = MovieEntityMapper()
    }

    @Test
    fun mapToCachedMapsData() {
        val movieEntity = MovieFactory.makeMovieEntity()
        val cachedMovie = movieEntityMapper.mapToCached(movieEntity)

        assertMovieDataEquality(movieEntity, cachedMovie)
    }

    @Test
    fun mapFromCachedMapsData() {
        val cachedMovie = MovieFactory.makeCachedMovie()
        val movieEntity = movieEntityMapper.mapFromCached(cachedMovie)

        assertMovieDataEquality(movieEntity, cachedMovie)
    }

    private fun assertMovieDataEquality(
        movieEntity: MovieEntity,
        cachedMovie: CachedMovie
    ) {
        assertEquals(movieEntity.overView, cachedMovie.overView)
        assertEquals(movieEntity.title, cachedMovie.title)
        assertEquals(movieEntity.posterPath, cachedMovie.posterPath)
    }

}