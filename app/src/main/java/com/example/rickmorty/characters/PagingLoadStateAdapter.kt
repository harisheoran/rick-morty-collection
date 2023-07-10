package com.example.rickmorty.characters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.R
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView

class PagingLoadStateAdapter(val retryCallback: () -> Unit) :
    LoadStateAdapter<PagingLoadStateAdapter.LoadStateViewHolder>() {

    class LoadStateViewHolder(itemView: View, retry: () -> Unit) : RecyclerView.ViewHolder(itemView) {

        init {
            itemView.findViewById<MaterialButton>(R.id.retry_button).setOnClickListener {
                retry.invoke()
            }
        }

        val progressBar = itemView.findViewById<ContentLoadingProgressBar>(R.id.progress_bar)
        val errorText = itemView.findViewById<MaterialTextView>(R.id.error_msg)
        fun bind(loadState: LoadState) {
            if (loadState is LoadState.Error) {
                errorText.text = "Try Again later..."
            }
            progressBar.isVisible = loadState is LoadState.Loading
            itemView.findViewById<MaterialButton>(R.id.retry_button).isVisible = loadState !is LoadState.Error
            errorText.isVisible = loadState !is LoadState.Loading
        }
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(parent: ViewGroup, loadState: LoadState): LoadStateViewHolder {
        return LoadStateViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.model_network_state, parent, false),
            retry = retryCallback
        )
    }
}