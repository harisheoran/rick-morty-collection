package com.example.rickmorty.episodes.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rickmorty.domain.models.Episode
import com.example.rickmorty.episodes.EpisodeRepository
import kotlinx.coroutines.launch

class EpisodeDetailsViewModel : ViewModel() {

    private val repository = EpisodeRepository()

    private val _episodeLiveData = MutableLiveData<Episode?>()
    val episodeLiveData: LiveData<Episode?> = _episodeLiveData

    fun fetchEpisodeById(episodeId: Int) {
        viewModelScope.launch {
            val episode = repository.getEpisodeById(episodeId)
            _episodeLiveData.postValue(episode)
        }
    }
}