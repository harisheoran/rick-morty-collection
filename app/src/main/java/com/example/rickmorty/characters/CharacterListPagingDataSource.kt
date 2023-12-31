package com.example.rickmorty.characters

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.network.response.GetCharacterByIdResponse

private const val TAG = "PageResult"


class CharacterListPagingDataSource(
    private val repository: CharacterListRepository
) : PagingSource<Int, GetCharacterByIdResponse>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GetCharacterByIdResponse> {
        return try {
            val pageNumber = params.key ?: 1
            val pageResult = repository.getCharactersPage(pageNumber)
            val pageResultList = pageResult?.results

            var nextPageNumber: Int? = null
            if (pageResult?.info?.next != null) {
                val uri = Uri.parse(pageResult.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()
            }

            LoadResult.Page(
                data = pageResultList.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }

    }

    override fun getRefreshKey(state: PagingState<Int, GetCharacterByIdResponse>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)

        }
    }
}