package com.example.rickmorty.episodes

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.rickmorty.NavGraphDirections
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentEpisodeListBinding
import com.example.utils.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class EpisodeListFragment : BaseFragment<FragmentEpisodeListBinding>(R.layout.fragment_episode_list) {

    private val viewModel: EpisodeListViewModel by lazy {
        ViewModelProvider(this).get(EpisodeListViewModel::class.java)
    }

    private val epoxyContoller = EpisodeListPagingEpoxyContoller(::onEpisodeClick)

    override fun FragmentEpisodeListBinding.initializeOnViewCreated() {
        initViewModelData()
        this.epoxyEpisodeRecyclerView.setController(epoxyContoller)
    }

    private fun initViewModelData() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                epoxyContoller.submitData(it)
            }
        }
    }

    private fun onEpisodeClick(episodeId: Int) {
        val action = NavGraphDirections.actionGlobalToEpisodeDetailsFragment(episodeId)
        findNavController().navigate(action)
    }
}