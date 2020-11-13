package com.nhat.presentation.factory

import com.nhat.domain.model.DomainMovieModel
import com.nhat.presentation.factory.DataFactory.Factory.randomLong
import com.nhat.presentation.factory.DataFactory.Factory.randomUuid
import com.nhat.presentation.model.MovieView

/**
 * Factory class for DomainMovieModel related instances
 */
class MovieFactory {

    companion object Factory {

        fun makeMovieList(count: Int): List<DomainMovieModel> {
            val list = mutableListOf<DomainMovieModel>()
            repeat(count) {
                list.add(makeMovieModel())
            }
            return list
        }

        private fun makeMovieModel(): DomainMovieModel {
            return DomainMovieModel(
                randomLong(),
                randomUuid(),
                randomUuid(),
                randomUuid(),
                randomLong().toString(),
                randomLong().toDouble()
            )
        }

        fun makeMovieViewList(count: Int): List<MovieView> {
            val list = mutableListOf<MovieView>()
            repeat(count) {
                list.add(makeMovieView())
            }
            return list
        }

        private fun makeMovieView(): MovieView {
            return MovieView(
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