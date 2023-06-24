package com.example.rickmorty

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {
    @GET("character/{character-id}")
    suspend fun getCharacterById(@Path(value = "character-id") characterId: Int): Response<GetCharacterByIdResponse>
}