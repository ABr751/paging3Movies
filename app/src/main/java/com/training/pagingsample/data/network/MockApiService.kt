package com.training.pagingsample.data.network

import android.app.Application
import com.google.gson.Gson
import com.training.pagingsample.data.model.MovieResponse
import com.training.pagingsample.data.network.response.MovieListResponse
import retrofit2.Response
import retrofit2.mock.BehaviorDelegate
import java.io.BufferedReader
import java.io.InputStreamReader

class MockApiService(
    private val delegate: BehaviorDelegate<Api>,
    private val serializer: Gson,
    private val app: Application
) : Api {
    override suspend fun getLocalMovies(page: Int): Response<MovieResponse> {
        val response = serializer.fromJson(stringFromFile("page$page"),MovieResponse::class.java)
        return delegate.returningResponse(response).getLocalMovies(page)
    }

    private fun stringFromFile(filePath: String): String {
        val stream = app.resources.assets.open("$filePath.json")
        val reader = BufferedReader(InputStreamReader(stream))
        val line = reader.readLines().joinToString(separator = "\n")
        reader.close()
        stream.close()
        return line
    }

}