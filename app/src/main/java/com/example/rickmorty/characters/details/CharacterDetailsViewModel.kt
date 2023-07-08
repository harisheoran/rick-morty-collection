package com.example.rickmorty.characters.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.models.Character
import kotlinx.coroutines.launch


class CharacterDetailsViewModel : ViewModel() {
    private val repository = CharacterDetailsRepository()

    private val _characterByIdLiveData = MutableLiveData<Character?>()
    val characterByIdLiveData: LiveData<Character?> = _characterByIdLiveData

    fun refreshCharacter(characterId: Int) {
        viewModelScope.launch {
            val characterResponse = repository.getCharacterById(characterId)
            _characterByIdLiveData.postValue(characterResponse)
        }
    }
}