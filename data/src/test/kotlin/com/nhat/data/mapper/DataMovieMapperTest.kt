package com.nhat.data.mapper

import com.nhat.data.factory.MovieFactory
import com.nhat.data.model.MovieEntity
import com.nhat.domain.model.DomainMovieModel
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import kotlin.test.assertEquals

@RunWith(JUnit4::class)
class DataMovieMapperTest {

    private lateinit var dataMovieMapper: DataMovieMapper

    @Before
    fun setUp() {
        dataMovieMapper = DataMovieMapper()
    }

    @Test
    fun mapFromEntityMapsData() {
        val entity = MovieFactory.makeMovieEntity()
        val model = dataMovieMapper.mapFromEntity(entity)

        assertMovieDataEquality(entity, model)
    }

    @Test
    fun mapToEntityMapsData() {
        val cachedMovie = MovieFactory.makeMovie()
        val entity = dataMovieMapper.mapToEntity(cachedMovie)

        assertMovieDataEquality(entity, cachedMovie)
    }

    private fun assertMovieDataEquality(
        movieEntity: MovieEntity,
        domainMovieModel: DomainMovieModel
    ) {
        assertEquals(movieEntity.overView, domainMovieModel.overView)
        assertEquals(movieEntity.title, domainMovieModel.title)
        assertEquals(movieEntity.posterPath, domainMovieModel.posterPath)
    }

}