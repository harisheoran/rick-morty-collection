package com.example.rickmorty.network

import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetCharactersPageResponse
import com.example.rickmorty.network.response.GetEpisodeByIdResponse
import com.example.rickmorty.network.response.GetEpisodesPageResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RickAndMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path(value = "character-id") characterId: Int): Response<GetCharacterByIdResponse>

    @GET(value = "character/")
    suspend fun getCharactersPage(@Query(value = "page") pageIndex: Int): Response<GetCharactersPageResponse>

    @GET(value = "character/")
    suspend fun getCharactersPageByName(
        @Query(value = "name") characterName: String,
        @Query(value = "page") pageIndex: Int
    ): Response<GetCharactersPageResponse>


    @GET(value = "episode/{episode_id}")
    suspend fun getEpisodeById(@Path(value = "episode_id") episodeId: Int): Response<GetEpisodeByIdResponse>

    @GET(value = "episode/{episode_range}")
    suspend fun getEpisodeRange(@Path(value = "episode_range") episodeRange: String): Response<List<GetEpisodeByIdResponse>>

    @GET(value = "episode/")
    suspend fun getEpisodePage(@Query(value = "page") pageIndex: Int): Response<GetEpisodesPageResponse>

    @GET(value = "character/{character_range}")
    suspend fun getCharacterRange(@Path(value = "character_range") characterRange: List<String>): Response<List<GetCharacterByIdResponse>>


}