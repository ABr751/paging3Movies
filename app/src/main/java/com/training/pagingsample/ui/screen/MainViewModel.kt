package com.training.pagingsample.ui.screen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.training.pagingsample.data.model.Content
import com.training.pagingsample.data.model.Movie
import com.training.pagingsample.data.repository.Repository
import com.training.pagingsample.data.repository.paged.LocalMoviePagingSource
import kotlinx.coroutines.flow.Flow

class MainViewModel(private val repository: Repository) : ViewModel() {

    val localMovies: Flow<PagingData<Content>> = fetchLocalMovies(false)

    fun fetchLocalMovies(reset: Boolean): Flow<PagingData<Content>> {
        return Pager(PagingConfig(20)) {
            LocalMoviePagingSource(repository, searchTerm.value, reset)
        }.flow
    }

    val searchTerm = MutableLiveData<String>()
    var triggerSearch: Boolean = false
}

sealed class LocalMovieModel {
    data class MovieItem(val movie: Movie) : LocalMovieModel()
    data class SeparatorItem(val description: String) : LocalMovieModel()
}
