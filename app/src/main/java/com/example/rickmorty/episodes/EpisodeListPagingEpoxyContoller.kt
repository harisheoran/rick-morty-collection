package com.example.rickmorty.episodes

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelEpisodeHeaderBinding
import com.example.rickmorty.databinding.ModelEpisodesPageBinding
import com.example.rickmorty.domain.models.Episode

class EpisodeListPagingEpoxyContoller(
    private val onEpisodeClicked: (Int) -> Unit
) : PagingDataEpoxyController<EpisodeUIModel>() {
    override fun buildItemModel(currentPosition: Int, item: EpisodeUIModel?): EpoxyModel<*> {

        return when (item!!) {
            is EpisodeUIModel.Item -> {
                val episode = (item as EpisodeUIModel.Item).episode
                EpisodesPageModel(
                    episode = episode,
                    onEpisodeClick = onEpisodeClicked
                ).id("episode_${episode.id}")
            }

            is EpisodeUIModel.Header -> {
                val headerText = (item as EpisodeUIModel.Header).title
                EpisodeHeaderModel(
                    title = headerText
                ).id("header_${headerText}")
            }
        }
    }

    data class EpisodesPageModel(
        val episode: Episode,
        val onEpisodeClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodesPageBinding>(R.layout.model_episodes_page) {
        override fun ModelEpisodesPageBinding.bind() {
            textEpisodeName.text = episode.name
            textAirDate.text = episode.air_date
            badgeEpisodeNumber.text = episode.getFormattedSeasonShort()
            root.setOnClickListener {
                onEpisodeClick(episode.id)
            }
        }
    }

    data class EpisodeHeaderModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelEpisodeHeaderBinding>(R.layout.model_episode_header) {
        override fun ModelEpisodeHeaderBinding.bind() {
            textView.text = title
        }

    }

}