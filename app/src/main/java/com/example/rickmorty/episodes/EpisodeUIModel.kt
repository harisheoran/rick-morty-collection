package com.example.rickmorty.episodes

import com.example.rickmorty.domain.models.Episode

sealed class EpisodeUIModel {

    class Item(val episode: Episode) : EpisodeUIModel()
    class Header(val title: String) : EpisodeUIModel()
}