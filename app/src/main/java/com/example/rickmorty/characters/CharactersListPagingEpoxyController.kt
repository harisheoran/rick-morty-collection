package com.example.rickmorty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelCharacterListItemBinding
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharactersListPagingEpoxyController : PagingDataEpoxyController<GetCharacterByIdResponse>() {
    override fun buildItemModel(currentPosition: Int, item: GetCharacterByIdResponse?): EpoxyModel<*> {
        return GridModelForEpoxyPaging(name = item!!.name, imageUrl = item!!.image).id(item.id)
    }

    data class GridModelForEpoxyPaging(
        val name: String,
        val imageUrl: String
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            characterListTextView.text = name
            Picasso.get().load(imageUrl).into(characterListImageView)

        }

    }
}