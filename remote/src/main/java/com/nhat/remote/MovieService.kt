package com.nhat.remote

import com.nhat.remote.model.RemoteMovieModel
import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Defines the abstract methods used for interacting with the Movie API
 */
interface MovieService {

    @GET("discover/movie")
    fun getMovies(
        @Query("api_key") apiKey : String = API_KEY,
        @Query("page") page: Int = 1 //TODO pagination
    ): Flowable<ResponseList<RemoteMovieModel>>

    @GET("movie/{movie_id}")
    fun getMovie(
        @Query("api_key") apiKey: String = API_KEY,
        @Path("movie_id") id: Long
    ): Flowable<RemoteMovieModel>

    @GET("search/movie")
    fun searchMovies(
        @Query("api_key") apiKey: String = API_KEY,
        @Query("page") page: Int = 1,
        @Query("query") query: String = ""
    ): Flowable<ResponseList<RemoteMovieModel>>

    companion object {
        const val API_KEY = "e34858088fc0b8b06d46ba3a35b5e4ea"
    }
}