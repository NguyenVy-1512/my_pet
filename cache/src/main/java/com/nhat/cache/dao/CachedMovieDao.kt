package com.nhat.cache.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nhat.cache.db.constants.MovieConstants
import com.nhat.cache.model.CachedMovie

@Dao
abstract class CachedMovieDao {

    @Query(MovieConstants.QUERY_MOVIES)
    abstract fun getMovies(): List<CachedMovie>

    @Query(MovieConstants.QUERY_MOVIE)
    abstract fun getMovie(id: Long): CachedMovie

    @Query(MovieConstants.DELETE_ALL_MOVIES)
    abstract fun clearMovies()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    abstract fun insertMovie(cachedMovie: CachedMovie)

}