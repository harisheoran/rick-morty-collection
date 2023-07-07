package com.example.rickmorty.episodes.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import com.airbnb.epoxy.EpoxyRecyclerView
import com.example.rickmorty.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class EpisodeDetailsFragment : BottomSheetDialogFragment() {

    private val args: EpisodeDetailsFragmentArgs by navArgs()

    private val viewModel: EpisodeDetailsViewModel by lazy {
        ViewModelProvider(this).get(EpisodeDetailsViewModel::class.java)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_episode_details, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.episodeLiveData.observe(viewLifecycleOwner) {
            if (it == null) {
                return@observe
            }
            view.findViewById<TextView>(R.id.episodeNumberTextView).text = it.getFormattedSeason()
            view.findViewById<TextView>(R.id.episodeNameTextView).text = it.name
            view.findViewById<TextView>(R.id.episodeAirDateTextView).text = it.air_date

            view.findViewById<EpoxyRecyclerView>(R.id.character_details_epoxyRecyclerView).setControllerAndBuildModels(
                EpisodeDetailsEpoxyController(it.characters)
            )
        }

        viewModel.fetchEpisodeById(args.episodeId)

    }

}