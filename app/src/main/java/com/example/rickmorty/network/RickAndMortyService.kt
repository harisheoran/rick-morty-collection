package com.example.rickmorty.network

import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetCharactersPageResponse
import com.example.rickmorty.network.response.GetEpisodeByIdReponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path(value = "character-id") characterId: Int): Response<GetCharacterByIdResponse>

    @GET(value = "character")
    suspend fun getCharactersPage(@Query(value = "page") pageIndex: Int): Response<GetCharactersPageResponse>

    @GET(value = "episode/{episode_id}")
    suspend fun getEpisodeById(@Path(value = "episode_id") episodeId: Int): Response<GetEpisodeByIdReponse>

    @GET(value = "episode/{episode_range}")
    suspend fun getEpisodeRange(@Path(value = "episode_range") episodeRange: String): Response<List<GetEpisodeByIdReponse>>
}