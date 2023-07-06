package com.example.rickmorty.network

import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.example.rickmorty.network.response.GetCharactersPageResponse
import com.example.rickmorty.network.response.GetEpisodeByIdResponse
import com.example.rickmorty.network.response.GetEpisodesPageResponse
import retrofit2.Response

class ApiClient(private val rickAndMortyService: RickAndMortyService) {

    // Get single character using id
    suspend fun getCharacterById(characterId: Int): SimpleResponse<GetCharacterByIdResponse> {
        return safeApiCall { rickAndMortyService.getCharacterById(characterId) }
    }

    // get multiple character using page index
    suspend fun getCharactersPages(pageIndex: Int): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPage(pageIndex) }
    }

    suspend fun getCharactersPageByName(
        characterName: String,
        pageIndex: Int
    ): SimpleResponse<GetCharactersPageResponse> {
        return safeApiCall { rickAndMortyService.getCharactersPageByName(characterName, pageIndex) }
    }

    // get single episode using id
    suspend fun getEpisodeById(episodeId: Int): SimpleResponse<GetEpisodeByIdResponse> {
        return safeApiCall { rickAndMortyService.getEpisodeById(episodeId) }
    }

    // get multiple episodes using range of ids
    suspend fun getEpisodeRange(episodeRange: String): SimpleResponse<List<GetEpisodeByIdResponse>> {
        return safeApiCall { rickAndMortyService.getEpisodeRange(episodeRange) }
    }

    // get multiple episodes
    suspend fun getEpisodePage(pageIndex: Int): SimpleResponse<GetEpisodesPageResponse> {
        return safeApiCall { rickAndMortyService.getEpisodePage(pageIndex) }
    }

    suspend fun getCharacterRange(characterRange: List<String>): SimpleResponse<List<GetCharacterByIdResponse>> {
        return safeApiCall { rickAndMortyService.getCharacterRange(characterRange) }
    }

    private inline fun <T> safeApiCall(apiCall: () -> Response<T>): SimpleResponse<T> {
        return try {
            // apiCall.invoke(), this is where the network request is going to happen and return response data if successful
            SimpleResponse.success(apiCall.invoke())
        } catch (e: Exception) {
            SimpleResponse.failure(e)
        }
    }
}