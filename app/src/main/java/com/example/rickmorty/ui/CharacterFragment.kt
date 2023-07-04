package com.example.rickmorty.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.CharacterDetailsEpoxyController
import com.example.rickmorty.R
import com.example.rickmorty.SharedViewModel

class CharacterFragment : Fragment() {

    private val viewModel: SharedViewModel by lazy {
        ViewModelProvider(this).get(SharedViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController()

    private val args: CharacterFragmentArgs by navArgs()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_character, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //     supportActionBar?.setDisplayHomeAsUpEnabled(true)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) {
            //update the data that is in epoxy controller
            epoxyController.character = it

            if (it == null) {
                Toast.makeText(requireActivity(), "Unsuccessful Request", Toast.LENGTH_SHORT).show()
                return@observe
            }
        }

        viewModel.refreshCharacter(characterId = args.characterId)
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }
}