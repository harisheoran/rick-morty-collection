package com.example.rickmorty.domain.mappers

import com.example.rickmorty.domain.models.Episode
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetEpisodeByIdResponse

object EpisodeMapper {
    fun mapTo(
        response: GetEpisodeByIdResponse,
        responseCharacters: List<GetCharacterByIdResponse> = emptyList()
    ): Episode {
        return Episode(
            id = response.id,
            name = response.name,
            air_date = response.air_date,
            season = getSeasonFromEpisodeString(response.episode),
            episodeNum = getEpisodeFromEpisodeString(response.episode),
            characters = responseCharacters.map {
                CharacterMapper.mapTo(it)
            }
        )
    }

    private fun getSeasonFromEpisodeString(episode: String): Int {
        val endIndex = episode.indexOfFirst {
            it.equals('e', true)
        }
        if (endIndex == -1) {
            return 0
        }
        return episode.substring(1, endIndex).toInt() ?: 0
    }

    private fun getEpisodeFromEpisodeString(episode: String): Int {
        val startIndex = episode.indexOfFirst {
            it.equals('e', true)
        }
        if (startIndex == -1) {
            return 0
        }
        return episode.substring(startIndex + 1, episode.length).toInt() ?: 0

    }
}