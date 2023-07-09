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
import com.airbnb.lottie.LottieAnimationView
import com.example.rickmorty.NetworkViewModel
import com.example.rickmorty.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : Fragment() {
    private lateinit var networkViewModel: NetworkViewModel
    private val epoxyController = CharacterListPagingEpoxyController(
        ::onCharacterClicked
    )

    private val viewModel: CharacterListViewModel by lazy {
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
        view.findViewById<LottieAnimationView>(R.id.loading).visibility = View.VISIBLE
        view.findViewById<LottieAnimationView>(R.id.no_internet).visibility = View.GONE

        networkViewModel = ViewModelProvider(this).get(NetworkViewModel::class.java)


        networkViewModel.getNetworkState().observe(viewLifecycleOwner, {
            if (it) {
                view.findViewById<LottieAnimationView>(R.id.loading).visibility = View.GONE
                view.findViewById<EpoxyRecyclerView>(R.id.epoxy_character_recycler_view).visibility = View.VISIBLE
                view.findViewById<LottieAnimationView>(R.id.no_internet).visibility = View.GONE
                lifecycleScope.launch {
                    viewModel.pagingDataFlow.collectLatest {
                        epoxyController.submitData(it)
                    }
                }
            } else {
                view.findViewById<LottieAnimationView>(R.id.loading).visibility = View.GONE
                view.findViewById<EpoxyRecyclerView>(R.id.epoxy_character_recycler_view).visibility = View.GONE
                view.findViewById<LottieAnimationView>(R.id.no_internet).visibility = View.VISIBLE
            }
        })


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