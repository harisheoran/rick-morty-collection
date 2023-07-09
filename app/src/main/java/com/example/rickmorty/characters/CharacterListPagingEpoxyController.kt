package com.example.rickmorty.characters

import com.airbnb.epoxy.EpoxyModel
import com.airbnb.epoxy.paging3.PagingDataEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelCharacterListItemBinding
import com.example.rickmorty.databinding.ModelCharacterListTitleBinding
import com.example.rickmorty.epoxy.LoadingEpoxyModel
import com.example.rickmorty.network.response.GetCharacterByIdResponse
import com.squareup.picasso.Picasso

class CharacterListPagingEpoxyController(
    private val onCharacterClicked: (Int) -> Unit
) : PagingDataEpoxyController<GetCharacterByIdResponse>() {

    var isLoading: Boolean = true
        set(value) {
            field = value
            if (field) {
                requestModelBuild()
            }
        }

    var characterList: GetCharacterByIdResponse? = null
        set(value) {
            field = value
            if (field != null) {
                isLoading = false
                requestModelBuild()
            }
        }

    override fun buildItemModel(currentPosition: Int, item: GetCharacterByIdResponse?): EpoxyModel<*> {

        if (item != null) {
            isLoading = false
        }


        return GridModelForEpoxyPaging(
            characterId = item!!.id,
            name = item!!.name,
            imageUrl = item!!.image,
            onCharacterClicked = onCharacterClicked
        ).id(item.id)


    }

    override fun addModels(models: List<EpoxyModel<*>>) {
        if (isLoading || models.isEmpty()) {
            LoadingEpoxyModel().id("load_list").addTo(this)
        }
        super.addModels(models)
    }

    data class GridModelForEpoxyPaging(
        val characterId: Int,
        val name: String,
        val imageUrl: String,
        val onCharacterClicked: (Int) -> Unit
    ) : ViewBindingKotlinModel<ModelCharacterListItemBinding>(R.layout.model_character_list_item) {
        override fun ModelCharacterListItemBinding.bind() {
            characterListTextView.text = name
            Picasso.get().load(imageUrl).into(characterListImageView)
            root.setOnClickListener {
                onCharacterClicked(characterId)
            }
        }
    }

    data class TitleForGridModels(
        val title: String
    ) : ViewBindingKotlinModel<ModelCharacterListTitleBinding>(R.layout.model_character_list_title) {
        override fun ModelCharacterListTitleBinding.bind() {
            titleTextView.text = title
        }

        override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
            return totalSpanCount
        }
    }
}