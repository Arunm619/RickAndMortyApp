package io.arunbuilds.rickandmorty.ui.episodes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.CombinedLoadStates
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.rickandmorty.databinding.FragmentEpisodesBinding
import io.arunbuilds.rickandmorty.databinding.ItemEpisodeBinding
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import io.arunbuilds.rickandmorty.ui.episodes.adapter.EpisodesAdapter
import io.arunbuilds.rickandmorty.util.connectivity.ConnectivityManager
import io.arunbuilds.rickandmorty.util.hide
import io.arunbuilds.rickandmorty.util.observe
import io.arunbuilds.rickandmorty.util.show
import io.arunbuilds.rickandmorty.util.showForSecond
import javax.inject.Inject

@AndroidEntryPoint
class EpisodesFragment : Fragment(), EpisodesAdapter.EpisodeClickListener {

    private val viewModel by viewModels<EpisodesViewModel>()

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    @Inject
    lateinit var episodesAdapter: EpisodesAdapter


    private var _binding: FragmentEpisodesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEpisodesBinding.inflate(inflater, container, false)
        val root: View = binding.root
        connectivityManager.registerConnectionObserver(this)
        setUpRecyclerViews()
        observeLiveData()
        return root
    }


    private fun setUpRecyclerViews() {
        binding.rvEpisodes.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            episodesAdapter.episodeClickListener = this@EpisodesFragment
            adapter = episodesAdapter
        }

        // On Refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        // Load State Listener
        episodesAdapter.addLoadStateListener { loadState: CombinedLoadStates ->

            // Setup Empty View
            val displayEmpty = loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    episodesAdapter.itemCount < 1
            if (displayEmpty) {
                binding.rvEpisodes.showEmptyView(msg = "No items...")
            } else {
                binding.rvEpisodes.hideAllViews()
            }

            binding.swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            // Setup Error View
            if (loadState.source.refresh is LoadState.Error) {
                binding.rvEpisodes.setOnRetryClickListener {
                    episodesAdapter.retry()
                }
                binding.rvEpisodes.hideAllViews()
                binding.rvEpisodes.showErrorView(msg = (loadState.source.refresh as LoadState.Error).error.message)
            }
        }
    }

    private fun observeLiveData() {

        // monitor network status
        observe(connectivityManager.isNetworkAvailable) { isNetworkAvailable ->
            binding.networkStatusBanner.run {
                if (isNetworkAvailable) {
                    // when the error is currently being shown and when the internet is back, show internet is back for 3 seconds.
                    if (binding.rvEpisodes.isErrorViewShown()) {
                        viewModel.onRefresh()
                        if (tvInternetNotAvailableStatusBanner.isVisible) {
                            tvInternetNotAvailableStatusBanner.hide()
                            tvInternetAvailableStatusBanner.showForSecond(3)
                        }
                    }
                    // when the list is present with few items but internet was not available, but is back now remove the no internet banner
                    if (tvInternetNotAvailableStatusBanner.isVisible) {
                        tvInternetNotAvailableStatusBanner.hide()
                    }
                } else {
                    // Show no internet card when there is no internet :P
                    tvInternetAvailableStatusBanner.hide()
                    tvInternetNotAvailableStatusBanner.show()
                }
            }
        }
        // collect pages and submit to list
        observe(viewModel.episodes) { episodes ->
            episodesAdapter.submitData(viewLifecycleOwner.lifecycle, episodes)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        connectivityManager.unregisterConnectionObserver(this)
    }

    override fun onEpisodeClicked(binding: ItemEpisodeBinding, episode: Episode) {
        Toast.makeText(
            requireContext(),
            "Hey you have tapped on ${binding.tvName.text} of $episode",
            Toast.LENGTH_LONG
        ).show()
    }
}