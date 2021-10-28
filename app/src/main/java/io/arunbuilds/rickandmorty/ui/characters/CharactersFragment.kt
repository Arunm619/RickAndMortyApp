package io.arunbuilds.rickandmorty.ui.characters

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
import io.arunbuilds.rickandmorty.databinding.FragmentCharactersBinding
import io.arunbuilds.rickandmorty.databinding.ItemCharacterBinding
import io.arunbuilds.rickandmorty.model.response.characters.Character
import io.arunbuilds.rickandmorty.ui.characters.adapter.CharacterLoadStateAdapter
import io.arunbuilds.rickandmorty.ui.characters.adapter.CharactersAdapter
import io.arunbuilds.rickandmorty.util.connectivity.ConnectivityManager
import io.arunbuilds.rickandmorty.util.hide
import io.arunbuilds.rickandmorty.util.observe
import io.arunbuilds.rickandmorty.util.show
import io.arunbuilds.rickandmorty.util.showForSecond
import javax.inject.Inject

@AndroidEntryPoint
class CharactersFragment : Fragment(), CharactersAdapter.CharacterClickListener {

    private val viewModel by viewModels<CharactersViewModel>()
    private var _binding: FragmentCharactersBinding? = null

    @Inject
    lateinit var charactersAdapter: CharactersAdapter

    @Inject
    lateinit var connectivityManager: ConnectivityManager

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentCharactersBinding.inflate(inflater, container, false)
        val root: View = binding.root
        connectivityManager.registerConnectionObserver(this)
        setUpRecyclerViews()
        observeLiveData()
        return root
    }


    private fun setUpRecyclerViews() {
        binding.rvCharacter.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext())
            charactersAdapter.characterClickListener = this@CharactersFragment
            adapter = charactersAdapter.withLoadStateHeaderAndFooter(
                CharacterLoadStateAdapter { charactersAdapter.retry() },
                CharacterLoadStateAdapter { charactersAdapter.retry() }
            )
        }

        // On Refresh
        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.onRefresh()
        }

        // Load State Listener
        charactersAdapter.addLoadStateListener { loadState: CombinedLoadStates ->

            // Setup Empty View
            val displayEmpty = loadState.source.refresh is LoadState.NotLoading &&
                    loadState.append.endOfPaginationReached &&
                    charactersAdapter.itemCount < 1
            if (displayEmpty) {
                binding.rvCharacter.showEmptyView(msg = "No items...")
            } else {
                binding.rvCharacter.hideAllViews()
            }

            binding.swipeRefreshLayout.isRefreshing = loadState.source.refresh is LoadState.Loading
            // Setup Error View
            if (loadState.source.refresh is LoadState.Error) {
                binding.rvCharacter.setOnRetryClickListener {
                    charactersAdapter.retry()
                }
                binding.rvCharacter.hideAllViews()
                binding.rvCharacter.showErrorView(msg = (loadState.source.refresh as LoadState.Error).error.message)
            }
        }
    }

    private fun observeLiveData() {

        // monitor network status
        observe(connectivityManager.isNetworkAvailable) { isNetworkAvailable ->
            binding.networkStatusBanner.run {
                if (isNetworkAvailable) {
                    // when the error is currently being shown and when the internet is back, show internet is back for 3 seconds.
                    if (binding.rvCharacter.isErrorViewShown()) {
                        viewModel.onRefresh()
                        if (tvInternetNotAvailableStatusBanner.isVisible) {
                            tvInternetAvailableStatusBanner.showForSecond(3)
                            tvInternetNotAvailableStatusBanner.hide()
                        }
                    }
                } else {
                    // Show no internet card when there is no internet :P
                    tvInternetAvailableStatusBanner.hide()
                    tvInternetNotAvailableStatusBanner.show()
                }
            }
        }
        // collect pages and submit to list
        observe(viewModel.characters) { characters ->
            charactersAdapter.submitData(viewLifecycleOwner.lifecycle, characters)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        connectivityManager.unregisterConnectionObserver(this)
    }

    override fun onCharacterClicked(binding: ItemCharacterBinding, character: Character) {
        Toast.makeText(
            requireContext(),
            "Hey you have tapped on ${binding.tvName.text} of $character",
            Toast.LENGTH_LONG
        ).show()
    }
}