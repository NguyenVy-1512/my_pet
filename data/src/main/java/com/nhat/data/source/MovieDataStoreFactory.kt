package com.nhat.data.source

import com.nhat.data.repository.IMovieCache
import com.nhat.data.repository.MovieDataStore
import javax.inject.Inject

/**
 * Create an instance of a MovieDataStore
 */
open class MovieDataStoreFactory @Inject constructor(
    private val IMovieCache: IMovieCache,
    private val movieCacheDataStore: MovieCacheDataStore,
    private val movieRemoteDataStore: MovieRemoteDataStore
) {

    /**
     * Returns a DataStore based on whether or not there is content in the cache and the cache
     * has not expired
     */
    open fun retrieveDataStore(isCached: Boolean): MovieDataStore {
        if (isCached && !IMovieCache.isExpired()) {
            return retrieveCacheDataStore()
        }
        return retrieveRemoteDataStore()
    }

    /**
     * Return an instance of the Cache Data Store
     */
    open fun retrieveCacheDataStore(): MovieDataStore {
        return movieCacheDataStore
    }

    /**
     * Return an instance of the Remote Data Store
     */
    open fun retrieveRemoteDataStore(): MovieDataStore {
        return movieRemoteDataStore
    }

}