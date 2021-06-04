package com.training.pagingsample.data.network

import com.training.pagingsample.data.model.MovieResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {

    @GET("localMovies")
    suspend fun getLocalMovies(
        @Query("page") page: Int
    ) : Response<MovieResponse>
}