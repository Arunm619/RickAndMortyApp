package io.arunbuilds.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import io.arunbuilds.rickandmorty.databinding.ItemCharacterLoaderBinding
import javax.inject.Inject

class CharacterLoadStateAdapter @Inject constructor(
    private val retry: () -> Unit
) :
    LoadStateAdapter<CharacterLoadStateAdapter.CharacterLoadViewHolder>() {
    override fun onBindViewHolder(holder: CharacterLoadViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): CharacterLoadViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterLoaderBinding.inflate(inflater, parent, false)
        return CharacterLoadViewHolder(binding)
    }

    inner class CharacterLoadViewHolder(private val binding: ItemCharacterLoaderBinding) :
        RecyclerView.ViewHolder(binding.root) {

        private fun showErrorState() {
            with(binding) {
                btnRetry.setOnClickListener { retry() }
                btnRetry.visibility = View.VISIBLE

                progressBar.visibility = View.GONE
                tvErrorMsg.visibility = View.VISIBLE
            }
        }

        private fun showLoadingState() {
            with(binding) {
                btnRetry.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvErrorMsg.visibility = View.GONE
            }
        }

        fun bind(loadState: LoadState) {
            when (loadState) {
                is LoadState.NotLoading -> {
                    showLoadingState()
                }
                is LoadState.Loading -> {
                    showLoadingState()
                }
                is LoadState.Error -> {
                    showErrorState()
                }
            }


        }

    }
}

