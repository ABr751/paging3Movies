package com.training.pagingsample.data.repository

import com.training.pagingsample.data.model.MovieResponse
import com.training.pagingsample.data.model.Result
import com.training.pagingsample.data.network.MovieAppService

class Repository(private val service: MovieAppService) {

    suspend fun getLocalMovies(page: Int) : MovieResponse {
        return when(val result = service.getLocalMovies(page)){
            is Result.Success -> result.data
            is Result.Error -> throw result.error
        }
    }
}