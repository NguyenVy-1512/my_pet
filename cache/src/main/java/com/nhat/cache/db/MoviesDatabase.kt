package com.nhat.cache.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nhat.cache.dao.CachedMovieDao
import com.nhat.cache.model.CachedMovie
import javax.inject.Inject

@Database(entities = [CachedMovie::class], version = 1)
abstract class MoviesDatabase @Inject constructor() : RoomDatabase() {

    abstract fun cachedMovieDao(): CachedMovieDao

    private var INSTANCE: MoviesDatabase? = null

    private val sLock = Any()

    fun getInstance(context: Context): MoviesDatabase {
        if (INSTANCE == null) {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        MoviesDatabase::class.java, "movies.db"
                    ).build()
                }
                return INSTANCE!!
            }
        }
        return INSTANCE!!
    }

}