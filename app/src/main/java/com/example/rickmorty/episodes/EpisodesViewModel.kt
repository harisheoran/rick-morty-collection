package com.example.rickmorty.episodes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


class EpisodesViewModel : ViewModel() {
    private val repository = EpisodeRepository()

    val pager = Pager(
        // Configure how data is loaded by passing additional properties to
        // PagingConfig, such as prefetchDistance.
        config = PagingConfig(pageSize = 10, prefetchDistance = 20),
        pagingSourceFactory = { EpisodesPagingSource(repository = repository) }

    )

    val pagingDataFlow: Flow<PagingData<EpisodeUIModel>> = pager.flow.cachedIn(viewModelScope).map {
        it.insertSeparators { episodeUIModel: EpisodeUIModel?, episodeUIModel2: EpisodeUIModel? ->
            if (episodeUIModel == null) {
                return@insertSeparators EpisodeUIModel.Header("Season 1")
            }
            if (episodeUIModel == null || episodeUIModel2 == null) {
                return@insertSeparators null
            }
            if (episodeUIModel is EpisodeUIModel.Header || episodeUIModel2 is EpisodeUIModel.Header) {
                return@insertSeparators null
            }
            val ep1 = (episodeUIModel as EpisodeUIModel.Item).episode
            val ep2 = (episodeUIModel2 as EpisodeUIModel.Item).episode
            return@insertSeparators if (ep2.season != ep1.season) {
                EpisodeUIModel.Header("Season ${ep2.season}")
            } else {
                null
            }
        }
    }

}