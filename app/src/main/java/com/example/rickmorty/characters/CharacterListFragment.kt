package com.example.rickmorty.characters

import androidx.core.view.isVisible
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentCharacterListBinding
import com.example.utils.BaseFragment
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharacterListFragment : BaseFragment<FragmentCharacterListBinding>(R.layout.fragment_character__list) {

    private val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this).get(CharacterListViewModel::class.java)
    }

    private lateinit var characterAdapter: CharacterListAdapter

    override fun FragmentCharacterListBinding.initializeOnViewCreated() {
        characterAdapter = CharacterListAdapter(::onCharacterClicked)

        initViewModelData()
        initRecyclerView(this)
    }

    private fun initViewModelData() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }

    private fun initRecyclerView(binding: FragmentCharacterListBinding) {

        binding.characterRecyclerView.adapter =
            characterAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { characterAdapter.retry() },
                footer = PagingLoadStateAdapter { characterAdapter.retry() }
            )

        characterAdapter.addLoadStateListener { loadState ->
            binding.swipeRefresh?.isRefreshing =
                loadState.refresh is LoadState.Loading
            binding.characterRecyclerView?.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            binding.progressBar?.isVisible =
                loadState.source.refresh is LoadState.Loading
            binding.retryButton?.isVisible =
                loadState.source.refresh is LoadState.Error
        }

        binding.swipeRefresh.setOnRefreshListener {
            characterAdapter.refresh()
        }

        binding.retryButton.setOnClickListener {
            characterAdapter.retry()
        }

    }

    private fun onCharacterClicked(characterId: Int) {
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterFragment(characterId)
        findNavController().navigate(action)
    }

}