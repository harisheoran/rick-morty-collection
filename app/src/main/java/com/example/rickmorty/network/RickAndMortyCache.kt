package com.example.rickmorty.network

import com.example.rickmorty.domain.models.Character

object RickAndMortyCache {

    val characterMap = mutableMapOf<Int, Character>()
}