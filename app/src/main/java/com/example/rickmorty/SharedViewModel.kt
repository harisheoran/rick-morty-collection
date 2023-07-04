package com.example.rickmorty

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.network.RickAndMortyCache
import kotlinx.coroutines.launch


class SharedViewModel : ViewModel() {
    private val repository = SharedRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int) {

        // if that characterData is cached
        val characterCache = RickAndMortyCache.characterMap[characterId]
        if (characterCache != null) {
            _characterByIdLiveData.postValue(characterCache)
            return     // we dont update data when we return data from cache as our data is not changing
        }

        // make network request is not cached
        viewModelScope.launch {
            val response = repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(response)

            // update character cache map here if response id non null
            response?.let {
                RickAndMortyCache.characterMap[characterId] = it
            }
        }
    }
}