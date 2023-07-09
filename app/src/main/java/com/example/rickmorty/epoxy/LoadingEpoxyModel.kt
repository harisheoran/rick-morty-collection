package com.example.rickmorty.epoxy

import com.example.rickmorty.R
import com.example.rickmorty.ViewBindingKotlinModel
import com.example.rickmorty.databinding.ModelLoadingBinding

class LoadingEpoxyModel: ViewBindingKotlinModel<ModelLoadingBinding>(R.layout.model_loading){
    override fun ModelLoadingBinding.bind() {

    }

    override fun getSpanSize(totalSpanCount: Int, position: Int, itemCount: Int): Int {
        return totalSpanCount
    }
}