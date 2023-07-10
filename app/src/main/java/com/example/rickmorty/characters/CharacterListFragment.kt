package com.example.rickmorty.characters

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.core.widget.ContentLoadingProgressBar
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.rickmorty.NetworkViewModel
import com.example.rickmorty.R
import com.google.android.material.button.MaterialButton
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

    private lateinit var characterAdapter: CharacterListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_character__list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterAdapter = CharacterListAdapter(::onCharacterClicked)

        initViewModelData()
        initRecyclerView()

    }

    private fun initViewModelData() {
        lifecycleScope.launch {
            viewModel.pagingDataFlow.collectLatest {
                characterAdapter.submitData(it)
            }
        }
    }


    private fun initRecyclerView() {
        view?.findViewById<RecyclerView>(R.id.character_recycler_view)?.adapter =
            characterAdapter.withLoadStateHeaderAndFooter(
                header = PagingLoadStateAdapter { characterAdapter.retry() },
                footer = PagingLoadStateAdapter { characterAdapter.retry() }
            )


        characterAdapter.addLoadStateListener { loadState ->
            view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)?.isRefreshing =
                loadState.refresh is LoadState.Loading
            view?.findViewById<RecyclerView>(R.id.character_recycler_view)?.isVisible =
                loadState.source.refresh is LoadState.NotLoading
            view?.findViewById<ContentLoadingProgressBar>(R.id.progress_bar)?.isVisible =
                loadState.source.refresh is LoadState.Loading
            view?.findViewById<MaterialButton>(R.id.retry_button)?.isVisible =
                loadState.source.refresh is LoadState.Error

        }

        view?.findViewById<SwipeRefreshLayout>(R.id.swipe_refresh)?.setOnRefreshListener {
            characterAdapter.refresh()
        }

        view?.findViewById<MaterialButton>(R.id.retry_button)?.setOnClickListener {
            characterAdapter.retry()
        }

    }


    private fun onCharacterClicked(characterId: Int) {
        val action = CharacterListFragmentDirections.actionCharacterListFragmentToCharacterFragment(characterId)
        findNavController().navigate(action)
    }
}