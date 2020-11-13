package com.nhat.cache.test.factory

import com.nhat.cache.model.CachedMovie
import com.nhat.cache.test.factory.DataFactory.Factory.randomLong
import com.nhat.cache.test.factory.DataFactory.Factory.randomUuid
import com.nhat.data.model.MovieEntity

/**
 * Factory class for DomainMovieModel related instances
 */
class MovieFactory {

    companion object Factory {

        fun makeCachedMovie(): CachedMovie {
            return CachedMovie(
                randomLong(),
                randomUuid(),
                randomUuid(),
                randomUuid(),
                randomLong().toString(),
                randomLong().toDouble()
            )
        }

        fun makeMovieEntity(): MovieEntity {
            return MovieEntity(
                randomLong(),
                randomUuid(),
                randomUuid(),
                randomUuid(),
                randomLong().toString(),
                randomLong().toDouble()
            )
        }

        fun makeMovieEntityList(count: Int): List<MovieEntity> {
            val entities = mutableListOf<MovieEntity>()
            repeat(count) {
                entities.add(makeMovieEntity())
            }
            return entities
        }

        fun makeCachedMovieList(count: Int): List<CachedMovie> {
            val cachedMovies = mutableListOf<CachedMovie>()
            repeat(count) {
                cachedMovies.add(makeCachedMovie())
            }
            return cachedMovies
        }

    }

}