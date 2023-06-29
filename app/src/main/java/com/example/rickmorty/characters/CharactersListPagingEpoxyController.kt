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
import java.util.*

class CharactersListPagingEpoxyController(
    private val onCharacterClicked: (Int) -> Unit
) : PagingDataEpoxyController<GetCharacterByIdResponse>() {

    override fun buildItemModel(currentPosition: Int, item: GetCharacterByIdResponse?): EpoxyModel<*> {
        return GridModelForEpoxyPaging(
            characterId = item!!.id,
            name = item!!.name,
            imageUrl = item!!.image,
            onCharacterClicked = onCharacterClicked
        ).id(item.id)
    }

    // this function add all build models to adatpter,
    // override this to add new model or remove one
    // this require a list of EpoxyModel list of any type has that moment of any , that was returned from above function
    override fun addModels(models: List<EpoxyModel<*>>) {

        // loading status
        if (models.isEmpty()) {
            LoadingEpoxyModel().id("loading").addTo(this)
            return
        }

        TitleForGridModels("Main Family")
            .id("main_family_header")
            .addTo(this)

        super.addModels(models.subList(0, 5))

        (models.subList(5, models.size) as List<GridModelForEpoxyPaging>).groupBy {
            it.name[0].toUpperCase()
        }.forEach {
            val title = it.key.toString().toUpperCase(Locale.US)
            TitleForGridModels(title)
                .id(title)
                .addTo(this)
            super.addModels(it.value)
        }
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