package com.example.rickmorty.epoxy

import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelErrorLoadingBinding

data class ErrorEpoxyModel(
    val title: String
) : ViewBindingKotlinModel<ModelErrorLoadingBinding>(R.layout.model_loading_error) {
    override fun ModelErrorLoadingBinding.bind() {
        textView2.text = title
    }


}