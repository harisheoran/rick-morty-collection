package com.example.rickmorty

import com.airbnb.epoxy.CarouselModel_
import com.airbnb.epoxy.EpoxyController
import com.example.rickmorty.databinding.*
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.domain.models.Episode
import com.example.rickmorty.epoxy.LoadingEpoxyModel
import com.squareup.picasso.Picasso

class CharacterDetailsEpoxyController(
    private val onEpisodeClick: (Int) -> Unit
) : EpoxyController() {

    /* isLoading property is set to a new value, the custom setter is triggered.
     It updates the backing field with the new value and then checks if the new value is true.
     If it is, the requestModelBuild() function is called.
     */
    var isLoading: Boolean = true
        set(value) {                // set(value) This block of code is executed when the property is assigned a new value.
            field = value
            if (field) {
                requestModelBuild() // if it is loading, then we will re-build our UI, which will cause to show our loading view
            }
        }

    // if our response is not null then we re-build our UI and remove loading UI.
    var character: Character? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildModels() {
        if (isLoading) {
            // show loading state
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        // error handling
        if (character == null) {
            return
        }

        // otherwise add all models -> Header -> Image -> Data Points (it appears in order)
        HeaderEpoxyModel(
            name = character!!.name,
            gender = character!!.gender,
            status = character!!.status
        ).id("header").addTo(this)              // this id key mu st be unique and then add this model to this model

        ImageEpoxyModel(
            imgUrl = character!!.image
        ).id("image").addTo(this)

        if (character!!.episodeList.isNotEmpty()) {
            val episodeItems = character!!.episodeList.map {
                EpisodeModel(
                    episode = it,
                    onClick = onEpisodeClick
                ).id(it.id)
            }

            EpisodeLabelModel(title = "Episodes").id("episode_label").addTo(this)

            CarouselModel_()
                .id("episode_horizontal_carousel")
                .models(episodeItems)
                .numViewsToShowOnScreen(1.15f)
                .addTo(this)
        }

        DataPointEpoxyModel(
            label = "Origin",
            description = character!!.origin.name
        ).id("data_point_1").addTo(this)

        DataPointEpoxyModel(
            label = "Species",
            description = character!!.species
        ).id("data_point_2").addTo(this)

    }

    // character header model
    data class HeaderEpoxyModel(
        val name: String,
        val gender: String,
        val status: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsHeaderBinding>(R.layout.model_character_details_header) {

        // overriding bind funtion on the instance of viewbinding of this model
        override fun ModelCharacterDetailsHeaderBinding.bind() {
            nameTextView.text = name
            aliveTextView.text = status

            if (gender.equals("Male", ignoreCase = true)) {
                genderImageView.setImageResource(R.drawable.male_24)
            } else {
                genderImageView.setImageResource(R.drawable.female_24)
            }
        }
    }

    // character image model
    data class ImageEpoxyModel(
        val imgUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsImageBinding>(R.layout.model_character_details_image) {
        override fun ModelCharacterDetailsImageBinding.bind() {
            Picasso.get().load(imgUrl).into(characterImageView);
        }
    }

    // Data Point image model
    data class DataPointEpoxyModel(
        val label: String,
        val description: String
    ) : ViewBindingKotlinModel<ModelCharacterDetailsDataPointBinding>(R.layout.model_character_details_data_point) {
        override fun ModelCharacterDetailsDataPointBinding.bind() {
            labelTextView.text = label
            labelNameTextView.text = description
        }
    }

    data class EpisodeModel(
        val episode: Episode,
        val onClick: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelEpisodeCarouselItemBinding>(R.layout.model_episode_carousel_item) {
        override fun ModelEpisodeCarouselItemBinding.bind() {
            episodesTextView.text = episode.getFormattedSeasonShort()
            episodesDetailsTextView.text = "${episode.name}\n${episode.air_date}"
            root.setOnClickListener {
                onClick(episode.id)
            }
        }
    }

    data class EpisodeLabelModel(
        val title: String
    ) : ViewBindingKotlinModel<ModelEpisodeLabelBinding>(R.layout.model_episode_label) {
        override fun ModelEpisodeLabelBinding.bind() {
            labelTextView.text = title
        }
    }

}