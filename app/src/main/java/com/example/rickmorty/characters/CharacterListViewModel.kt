package com.example.rickmorty.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.flow.Flow

class CharacterListViewModel : ViewModel() {

    private val repository = CharacterListRepository()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 40
    )

    // Pager provides a public API for construting instance of Pagingdata which will be exposed in reactive streams
    // using paging config and paging data source
    val pager = Pager(
        config = pagingConfig,
        pagingSourceFactory = { CharacterListPagingDataSource(repository = repository) }
    )

    val pagingDataFlow: Flow<PagingData<GetCharacterByIdResponse>> = pager.flow.cachedIn(viewModelScope)


}