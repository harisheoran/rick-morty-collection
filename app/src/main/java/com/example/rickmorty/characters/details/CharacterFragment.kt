package com.example.rickmorty.characters.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.NavGraphDirections
import com.example.rickmorty.R

class CharacterFragment : Fragment() {

    private val viewModel: CharacterDetailsViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController(::onClickEpisode)

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
                findNavController().navigateUp()
                return@observe
            }
        }

        viewModel.refreshCharacter(characterId = args.characterId)
        val epoxyRecyclerView = view.findViewById<EpoxyRecyclerView>(R.id.epoxy_recycler_view)
        epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    private fun onClickEpisode(episodeId: Int) {
        val action = NavGraphDirections.actionGlobalToEpisodeDetailsFragment(episodeId)
        findNavController().navigate(action)
    }
}