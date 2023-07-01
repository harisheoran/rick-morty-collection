package com.example.rickmorty.domain.mappers

import com.example.rickmorty.domain.models.Episode
import com.example.rickmorty.network.response.GetEpisodeByIdReponse

object EpisodeMapper {

    fun mapTo(responseEpisode: GetEpisodeByIdReponse): Episode {
        return Episode(
            id = responseEpisode.id,
            name = responseEpisode.name,
            air_date = responseEpisode.air_date,
            episode = responseEpisode.episode
        )
    }
}