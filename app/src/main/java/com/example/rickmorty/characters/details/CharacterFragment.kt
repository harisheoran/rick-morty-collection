package com.example.rickmorty.characters.details

import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.rickmorty.NavGraphDirections
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentCharacterBinding
import com.example.utils.BaseFragment

class CharacterFragment : BaseFragment<FragmentCharacterBinding>(R.layout.fragment_character) {

    private val viewModel: CharacterDetailsViewModel by lazy {
        ViewModelProvider(this).get(CharacterDetailsViewModel::class.java)
    }

    private val epoxyController = CharacterDetailsEpoxyController(::onClickEpisode)

    private val args: CharacterFragmentArgs by navArgs()

    override fun FragmentCharacterBinding.initializeOnViewCreated() {
        initViewModelData()
        this.epoxyRecyclerView.setControllerAndBuildModels(epoxyController)
    }

    // observing viewmodel data
    private fun initViewModelData() {
        viewModel.refreshCharacter(characterId = args.characterId)

        viewModel.characterByIdLiveData.observe(viewLifecycleOwner) {
            //update the data that is in epoxy controller
            epoxyController.character = it

            if (it == null) {
                Toast.makeText(requireActivity(), "Unsuccessful Request", Toast.LENGTH_SHORT).show()
                findNavController().navigateUp()
                return@observe
            }
        }
    }

    private fun onClickEpisode(episodeId: Int) {
        val action = NavGraphDirections.actionGlobalToEpisodeDetailsFragment(episodeId)
        findNavController().navigate(action)
    }
}