package com.example.rickmorty.ui

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
import com.example.rickmorty.characters.CharactersListPagingEpoxyController
import com.example.rickmorty.characters.CharactersViewModel
import com.google.firebase.crashlytics.FirebaseCrashlytics
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {

    val epoxyController = CharactersListPagingEpoxyController(
        ::onCharacterClicked
    )

    val viewModel: CharactersViewModel by lazy {
        ViewModelProvider(this).get(CharactersViewModel::class.java)
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
        FirebaseCrashlytics.getInstance().recordException(
            RuntimeException("Character ID selected $characterId")
        )
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterFragment(
            characterId
        )
        findNavController().navigate(action)
    }
}