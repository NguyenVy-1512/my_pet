package com.nhat.remote.factory

import com.nhat.remote.ResponseList
import com.nhat.remote.factory.DataFactory.Factory.randomLong
import com.nhat.remote.factory.DataFactory.Factory.randomUuid
import com.nhat.remote.model.RemoteMovieModel

/**
 * Factory class for DomainMovieModel related instances
 */
class MovieFactory {

    companion object Factory {

        fun makeMoviesListResponse(): ResponseList<RemoteMovieModel> {
            return ResponseList(
                page = 1,
                totalResults = 10000,
                totalPages = 500,
                results = makeMovieModelList(5)
            )
        }

        private fun makeMovieModelList(count: Int): List<RemoteMovieModel> {
            val entities = mutableListOf<RemoteMovieModel>()
            repeat(count) {
                entities.add(makeModel())
            }
            return entities
        }

        fun makeModel(): RemoteMovieModel {
            return RemoteMovieModel(
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