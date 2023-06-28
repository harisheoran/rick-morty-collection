package com.example.rickmorty.characters

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import kotlinx.coroutines.flow.Flow

class CharactersViewModel : ViewModel() {

    private val repository = CharactersRepository()

    private val pagingConfig = PagingConfig(
        pageSize = 20,
        prefetchDistance = 40
    )

    val pager = Pager(
        config = pagingConfig,
        pagingSourceFactory = { CharactersPagingDataSource(repository = repository) }
    )

    val pagingDataFlow: Flow<PagingData<GetCharacterByIdResponse>> = pager.flow.cachedIn(viewModelScope)
}