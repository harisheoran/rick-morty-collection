package com.example.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.domain.models.Character
import com.example.rickmorty.episodes.details.EpisodeDetailsEpoxyController
import com.squareup.picasso.Picasso


//In Kotlin, the let function is a scoping function that allows you to perform operations on a non-null object within a safe context.
// It is particularly useful for executing a block of code only if the object is not null.
@BindingAdapter("setImage")
fun bindImage(imgView: ImageView, imgUrl: String?) {
    // it will only execute if imageUrl is non null
    imgUrl?.let {
        Picasso.get().load(imgUrl).into(imgView)
    }
}

@BindingAdapter("setEpoxyController")
fun setEpoxyController(epoxyRecyclerView: EpoxyRecyclerView, characters: List<Character>?) {
    epoxyRecyclerView.setControllerAndBuildModels(EpisodeDetailsEpoxyController(characters))
}