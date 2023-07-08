package com.example.rickmorty.characters

import com.example.rickmorty.network.NetworkLayer
import com.example.rickmorty.network.response.GetCharactersPageResponse

class CharacterListRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPages(pageIndex)

        if (request.failed || !request.isSucceed) {
            return null
        }
        return request.body
    }
}