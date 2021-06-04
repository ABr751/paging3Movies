package com.training.pagingsample.data.repository.paged

import androidx.paging.PagingSource
import com.training.pagingsample.data.model.Content
import com.training.pagingsample.data.repository.Repository

class LocalMoviePagingSource(
    private val repository: Repository,
    val query: String? = null,
    val reset: Boolean = false
) : PagingSource<Int, Content>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Content> {

        return try {
            val nextPage = params.key ?: 1
            val movieListResponse = repository.getLocalMovies(nextPage)
            val initialList = movieListResponse
            if (query.isNullOrEmpty()) {
                LoadResult.Page(
                    data = movieListResponse.page?.contentItems?.content!!,
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (movieListResponse.page?.pageNum == "3") null else movieListResponse.page?.pageNum?.toInt()
                        ?.plus(1)
                )
            } else {
                LoadResult.Page(
                    data = if (reset) initialList.page?.contentItems?.content!! else
                        movieListResponse.page?.contentItems?.content!!.filter {
                            it.name?.contains(query, true)!!
                        },
                    prevKey = if (nextPage == 1) null else nextPage - 1,
                    nextKey = if (movieListResponse.page?.pageNum == "3") null else movieListResponse.page?.pageNum?.toInt()
                        ?.plus(1)
                )
            }
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}