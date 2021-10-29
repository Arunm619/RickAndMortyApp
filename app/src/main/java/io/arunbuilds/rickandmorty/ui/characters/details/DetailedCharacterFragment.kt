package io.arunbuilds.rickandmorty.ui.characters.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import dagger.hilt.android.AndroidEntryPoint
import io.arunbuilds.rickandmorty.databinding.DetailedCharacterFragmentBinding
import io.arunbuilds.rickandmorty.util.status

@AndroidEntryPoint
class DetailedCharacterFragment : Fragment() {

    private val viewModel by viewModels<DetailedCharacterViewModel>()

    private val args: DetailedCharacterFragmentArgs by navArgs()

    private var _binding: DetailedCharacterFragmentBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = DetailedCharacterFragmentBinding.inflate(inflater, container, false)
        val root: View = binding.root
        with(args.character) {
            binding.apply {
                Glide.with(ivAvatar.context)
                    .load(image)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .into(ivAvatar)
                tvName.text = name
                tvStatus.status(status)
            }
        }
        return root
    }
}