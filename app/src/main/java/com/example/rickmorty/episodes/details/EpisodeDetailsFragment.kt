package com.example.rickmorty.episodes.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.example.rickmorty.R
import com.example.rickmorty.databinding.FragmentEpisodeDetailsBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EpisodeDetailsFragment : BottomSheetDialogFragment() {

    private var _binding: FragmentEpisodeDetailsBinding? = null
    private val binding get() = _binding!!

    private val args: EpisodeDetailsFragmentArgs by navArgs()

    private val viewModel: EpisodeDetailsViewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DataBindingUtil.inflate(inflater, R.layout.fragment_episode_details, container, false)
        val view = binding.root
        binding.lifecycleOwner = this
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.viewModel = viewModel
        viewModel.fetchEpisodeById(args.episodeId)
    }
}