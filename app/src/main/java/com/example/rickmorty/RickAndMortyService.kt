package com.example.rickmorty

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface RickAndMortyService {
    @GET("character/{character-id}")
    fun getCharacterById(@Path(value = "character-id") characterId: Int): Call<GetCharacterByIdResponse>
}