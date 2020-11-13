package com.nhat.remote.mapper

import com.nhat.remote.factory.MovieFactory
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
    fun mapFromRemoteMapsData() {
        val bufferooModel = MovieFactory.makeModel()
        val bufferooEntity = movieEntityMapper.mapFromRemote(bufferooModel)

        assertEquals(bufferooModel.overView, bufferooEntity.overView)
        assertEquals(bufferooModel.title, bufferooEntity.title)
        assertEquals(bufferooModel.posterPath, bufferooEntity.posterPath)
    }

}