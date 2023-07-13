package com.example.rickmorty.characters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.rickmorty.databinding.ModelCharacterListItemBinding
import com.example.rickmorty.network.response.GetCharacterByIdResponse

class CharacterListAdapter(private val onCharacterClick: (Int) -> Unit) :
    PagingDataAdapter<GetCharacterByIdResponse, RecyclerView.ViewHolder>(DiffCallback) {

    class CharacterViewHolder(private val binding: ModelCharacterListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(onCharacterClick: (Int) -> Unit, character: GetCharacterByIdResponse) {
            binding.character = character
            binding.executePendingBindings()
            binding.root.setOnClickListener {
                onCharacterClick(character.id)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = CharacterViewHolder(ModelCharacterListItemBinding.inflate(LayoutInflater.from(parent.context)))
        return view
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val myCharacter = getItem(position)
        (holder as? CharacterViewHolder)?.bind(onCharacterClick, myCharacter!!)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<GetCharacterByIdResponse>() {
        override fun areItemsTheSame(oldItem: GetCharacterByIdResponse, newItem: GetCharacterByIdResponse): Boolean {
            return oldItem.name == newItem.name
        }

        override fun areContentsTheSame(oldItem: GetCharacterByIdResponse, newItem: GetCharacterByIdResponse): Boolean {
            return oldItem.name == newItem.name
        }
    }
}

