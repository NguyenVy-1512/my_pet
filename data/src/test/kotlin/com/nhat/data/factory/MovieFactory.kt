package com.nhat.data.factory

import com.nhat.data.factory.DataFactory.Factory.randomLong
import com.nhat.data.factory.DataFactory.Factory.randomUuid
import com.nhat.data.model.MovieEntity
import com.nhat.domain.model.DomainMovieModel

/**
 * Factory class for DomainMovieModel related instances
 */
class MovieFactory {

    companion object Factory {

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

        fun makeMovie(): DomainMovieModel {
            return DomainMovieModel(
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

        fun makeMovieList(count: Int): List<DomainMovieModel> {
            val list = mutableListOf<DomainMovieModel>()
            repeat(count) {
                list.add(makeMovie())
            }
            return list
        }

    }

}