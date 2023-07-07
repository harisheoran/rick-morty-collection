package com.example.rickmorty.domain.mappers

import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetEpisodeByIdResponse

object CharacterMapper {
    fun mapTo(
        response: GetCharacterByIdResponse,
        networkEpisodesList: List<GetEpisodeByIdResponse> = emptyList()
    ): Character {
        return Character(
            episodeList = networkEpisodesList.map {
                EpisodeMapper.mapTo(it)
            },
            gender = response.gender,
            id = response.id,
            image = response.image,
            location = Character.Location(
                name = response.location.name,
                url = response.location.url
            ),
            name = response.name,
            origin = Character.Origin(
                name = response.origin.name,
                url = response.origin.url
            ),
            species = response.species,
            status = response.status
        )
    }
}