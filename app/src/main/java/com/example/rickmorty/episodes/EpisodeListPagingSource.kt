package com.example.rickmorty.episodes

import android.net.Uri
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickmorty.domain.mappers.EpisodeMapper

class EpisodeListPagingSource(
    private val repository: EpisodeListRepository
) : PagingSource<Int, EpisodeUIModel>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, EpisodeUIModel> {
        return try {
            var pageNumber = params.key ?: 1

            // do a network call
            val episodePageRequest = repository.fetchEpisodes(pageNumber)
            val episodeResult = episodePageRequest?.results

            var nextPageNumber: Int? = null
            if (episodePageRequest?.info?.next != null) {
                val uri = Uri.parse(episodePageRequest.info.next)
                val nextPageQuery = uri.getQueryParameter("page")
                nextPageNumber = nextPageQuery?.toInt()!!
            }

            val episodeList = episodeResult?.map {
                EpisodeUIModel.Item(EpisodeMapper.mapTo(it))
            }
            LoadResult.Page(
                data = episodeList.orEmpty(),
                prevKey = null,
                nextKey = nextPageNumber
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }


    }

    override fun getRefreshKey(state: PagingState<Int, EpisodeUIModel>): Int? {
        // Try to find the page key of the closest page to anchorPosition, from
        // either the prevKey or the nextKey, but you need to handle nullability
        // here:
        //  * prevKey == null -> anchorPage is the first page.
        //  * nextKey == null -> anchorPage is the last page.
        //  * both prevKey and nextKey null -> anchorPage is the initial page, so
        //    just return null.
        /* return state.anchorPosition?.let { anchorPosition ->
             val anchorPage = state.closestPageToPosition(anchorPosition)
             anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
         }*/
        TODO("fnr")
    }
}