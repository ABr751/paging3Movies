package com.training.pagingsample.data.network

import com.training.pagingsample.data.model.MovieResponse
import com.training.pagingsample.data.model.Result

class MovieAppService(private val api: Api) : BaseService() {

    suspend fun getLocalMovies(page: Int) : Result<MovieResponse> {
        return createCall { api.getLocalMovies(page) }
    }
}