package com.example.rickmorty

import com.example.rickmorty.domain.mappers.CharacterMapper
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.network.NetworkLayer
import com.example.rickmorty.network.RickAndMortyCache
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetEpisodeByIdReponse

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): Character? {

        // check the cache
        val cachedCharacter = RickAndMortyCache.characterMap[characterId]
        if (cachedCharacter != null) {
            return cachedCharacter
        }

        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed || !request.isSucceed) {
            return null
        }

        val networkEpisodes = getEpisodesFromCharacterResponse(request.body)

        val character = CharacterMapper.mapTo(
            response = request.body,
            networkEpisodesList = networkEpisodes
        )

        // update character cache
        RickAndMortyCache.characterMap[characterId] = character
        return character
    }

    private suspend fun getEpisodesFromCharacterResponse(characterResponse: GetCharacterByIdResponse): List<GetEpisodeByIdReponse> {

        val episodeRange = characterResponse.episode.map {
            it.substring(startIndex = it.lastIndexOf("/") + 1)
        }.toString()

        val request = NetworkLayer.apiClient.getEpisodeRange(episodeRange)

        if (request.failed || !request.isSucceed) {
            return emptyList()
        }

        return request.body
    }
}