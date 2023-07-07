package com.example.rickmorty.episodes

import com.example.rickmorty.domain.mappers.EpisodeMapper
import com.example.rickmorty.domain.models.Episode
import com.example.rickmorty.network.NetworkLayer
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetEpisodeByIdResponse
import com.example.rickmorty.network.response.GetEpisodesPageResponse

class EpisodeRepository {
    suspend fun fetchEpisodes(pageIndex: Int): GetEpisodesPageResponse? {
        val episodePageRequest = NetworkLayer.apiClient.getEpisodePage(pageIndex)

        if (episodePageRequest.failed || !episodePageRequest.isSucceed) {
            return null
        }
        return episodePageRequest.body
    }

    suspend fun getEpisodeById(episodeId: Int): Episode? {
        val request = NetworkLayer.apiClient.getEpisodeById(episodeId)

        if (!request.isSucceed) {
            return null
        }

        val characterList = getCharactersFromEpisodeResponse(request.body)
        return EpisodeMapper.mapTo(request.body, characterList)
    }

    suspend fun getCharactersFromEpisodeResponse(episodeByIdResponse: GetEpisodeByIdResponse): List<GetCharacterByIdResponse> {
        val characterList = episodeByIdResponse.characters.map {
            it.substring(it.lastIndexOf("/") + 1)
        }

        val request = NetworkLayer.apiClient.getCharacterRange(characterList)
        return request.bodyNullable ?: emptyList()
    }
}