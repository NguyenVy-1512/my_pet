package com.nhat.domain.test.factory

import com.nhat.domain.model.DomainMovieModel
import com.nhat.domain.test.factory.DataFactory.Factory.randomLong
import com.nhat.domain.test.factory.DataFactory.Factory.randomUuid

/**
 * Factory class for DomainMovieModel related instances
 */
class MovieFactory {

    companion object Factory {

        fun makeMovieList(count: Int): List<DomainMovieModel> {
            val list = mutableListOf<DomainMovieModel>()
            repeat(count) {
                list.add(makeMovie())
            }
            return list
        }

        private fun makeMovie(): DomainMovieModel {
            return DomainMovieModel(
                randomLong(),
                randomUuid(),
                randomUuid(),
                randomUuid(),
                randomLong().toString(),
                randomLong().toDouble()
            )
        }

    }

}