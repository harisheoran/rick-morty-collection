package com.example.rickmorty.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.NavGraphDirections
import com.example.rickmorty.R
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment : Fragment() {

    private val viewModel: EpisodesViewModel by lazy {
        ViewModelProvider(this).get(EpisodesViewModel::class.java)
    }

    private val epoxyContoller = EpisodeListPagingEpoxyContoller(::onEpisodeClick)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_episode_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                epoxyContoller.submitData(it)
            }
        }

        view.findViewById<EpoxyRecyclerView>(R.id.epoxy_episode_recycler_view)
            .setController(epoxyContoller)

    }

    private fun onEpisodeClick(episodeId: Int) {
        val action = NavGraphDirections.actionGlobalToEpisodeDetailsFragment(episodeId)
        findNavController().navigate(action)
    }

}