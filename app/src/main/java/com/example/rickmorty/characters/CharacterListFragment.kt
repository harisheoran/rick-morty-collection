package com.example.rickmorty.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    val epoxyController = CharacterListPagingEpoxyController(
        ::onCharacterClicked
    )

    val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this).get(CharacterListViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character__list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                epoxyController.submitData(it)
            }
        }
        view.findViewById<EpoxyRecyclerView>(R.id.epoxy_character_recycler_view)
            .setController(epoxyController)

    }

    private fun onCharacterClicked(characterId: Int) {

        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterFragment(
            characterId
        )
        findNavController().navigate(action)
    }
}