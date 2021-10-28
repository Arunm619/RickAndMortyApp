package io.arunbuilds.rickandmorty.ui.characters.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import io.arunbuilds.rickandmorty.databinding.ItemCharacterBinding
import io.arunbuilds.rickandmorty.model.response.characters.Character
import javax.inject.Inject

class CharactersAdapter @Inject constructor() :
    PagingDataAdapter<Character, CharactersAdapter.CharacterViewHolder>(COMPARATOR) {

    var characterClickListener: CharacterClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        return create(parent)
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        getItem(position)?.let {
            holder.bind(it)
        }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<Character>() {
            override fun areItemsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Character, newItem: Character): Boolean {
                return oldItem == newItem
            }
        }
    }


    inner class CharacterViewHolder(private val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            itemView.setOnClickListener {
                characterClickListener?.onCharacterClicked(
                    binding,
                    getItem(absoluteAdapterPosition) as Character
                )
            }
        }

        fun bind(character: Character) {
            with(binding) {
                Glide.with(ivPoster.context)
                    .load(character.image)
                    .into(ivPoster)
                tvName.text = character.name
                tvLocation.text = character.location.toString()
                tvSomeBigText.text = character.toString()
            }

        }
    }

    fun create(parent: ViewGroup): CharacterViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater, parent, false)
        return CharacterViewHolder(binding)
    }

    interface CharacterClickListener {
        fun onCharacterClicked(binding: ItemCharacterBinding, character: Character)
    }

}