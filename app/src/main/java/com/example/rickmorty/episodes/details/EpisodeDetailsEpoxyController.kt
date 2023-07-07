package com.example.rickmorty.episodes.details

import com.airbnb.epoxy.EpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelCharacterBottomSheetBinding
import com.example.rickmorty.domain.models.Character
import com.squareup.picasso.Picasso

class EpisodeDetailsEpoxyController(
    private val characterList: List<Character>
) : EpoxyController() {

    override fun buildModels() {
        characterList.forEach {
            CharacterDetailsBottomSheetModel(
                name = it.name,
                imageUrl = it.image
            ).id(it.id).addTo(this)
        }
    }

    data class CharacterDetailsBottomSheetModel(
        val name: String,
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterBottomSheetBinding>(R.layout.model_character_bottom_sheet) {
        override fun ModelCharacterBottomSheetBinding.bind() {
            characterNameTextView.text = name
            Picasso.get().load(imageUrl).into(characterImageView)
        }
    }
}