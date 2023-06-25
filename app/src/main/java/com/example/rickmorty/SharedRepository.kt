package com.example.rickmorty

class SharedRepository {
    suspend fun getCharacterById(characterId: Int): GetCharacterByIdResponse? {
        val request = NetworkLayer.apiClient.getCharacterById(characterId)

        if (request.failed) {
            return null
        }

        if (!request.isSucceed) {
            return null
        }

        return request.body
    }
}