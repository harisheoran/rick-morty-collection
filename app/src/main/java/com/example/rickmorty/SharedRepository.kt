package com.example.rickmorty

import com.example.rickmorty.domain.mappers.CharacterMapper
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.network.NetworkLayer
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetEpisodeByIdReponse

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSucceed) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)

        return CharacterMapper.mapTo(
            response = request.body,
            networkEpisodesList = networkEpisodes
        )
    }

    private suspend fun getEpisodesFromCharacterResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdReponse> {

        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSucceed){
            return emptyList()
        }


        return request.body
    }
}