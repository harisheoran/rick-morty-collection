package com.example.rickmorty.characters

import com.example.rickmorty.network.NetworkLayer

class CharactersRepository {

    suspend fun getCharactersPage(pageIndex: Int): GetCharactersPageResponse? {
        val request = NetworkLayer.apiClient.getCharactersPages(pageIndex)

        if (request.failed || !request.isSucceed) {
            return null
        }
        return request.body
    }
}