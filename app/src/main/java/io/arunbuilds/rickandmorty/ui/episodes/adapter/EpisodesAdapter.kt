package io.arunbuilds.rickandmorty.ui.episodes.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import io.arunbuilds.rickandmorty.databinding.ItemEpisodeBinding
import io.arunbuilds.rickandmorty.model.response.episodes.Episode
import javax.inject.Inject

class EpisodesAdapter @Inject constructor() :
    PagingDataAdapter<Episode, EpisodesAdapter.EpisodeViewHolder>(COMPARATOR) {

    var episodeClickListener: EpisodeClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Episode>() {
            override fun areItemsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Episode, newItem: Episode): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class EpisodeViewHolder(private val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                episodeClickListener?.onEpisodeClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as Episode
                )
            }
        }

        fun bind(episode: Episode) {
            with(binding) {
                episode.run {
                    tvName.text = name
                    tvAirDate.text = airDate
                    tvEpisode.text = this.episode
                }
            }
        }
    }

    fun create(parent: ViewGroup): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(inflater, parent, false)
        return EpisodeViewHolder(binding)
    }

    interface EpisodeClickListener {
        fun onEpisodeClicked(binding: ItemEpisodeBinding, episode: Episode)
    }

}