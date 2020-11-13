package com.nhat.cache.db.constants

/**
 * Defines constants for the Movies Table
 */
object MovieConstants {

    const val TABLE_NAME = "movies"

    const val QUERY_MOVIES = "SELECT * FROM $TABLE_NAME"

    const val QUERY_MOVIE = "SELECT * FROM $TABLE_NAME WHERE id = :id"

    const val DELETE_ALL_MOVIES = "DELETE FROM $TABLE_NAME"

}