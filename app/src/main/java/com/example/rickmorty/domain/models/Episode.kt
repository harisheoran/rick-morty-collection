package com.example.rickmorty.domain.models

data class Episode(
    val id: Int = 0,
    val name: String = "",
    val air_date: String = "",
    val season: Int = 0,
    val episodeNum: Int = 0,
    val characters: List<Character> = emptyList()
) {
    fun getFormattedSeason(): String {
        return "Season ${season} Episode ${episodeNum}"
    }

    fun getFormattedSeasonShort(): String {
        return "S.${season} E.${episodeNum}"
    }
}
