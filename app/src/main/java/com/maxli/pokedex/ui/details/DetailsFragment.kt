package com.maxli.pokedex.ui.details

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxli.pokedex.R
import com.maxli.pokedex.databinding.FragmentDetailsBinding
import com.maxli.pokedex.ui.common.Constants.Companion.FAVOURITE_KEY
import com.maxli.pokedex.until.TextViewExtension.Companion.makeLinks
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val detailsViewModel =
            ViewModelProvider(this)[DetailsViewModel::class.java]

        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        detailsViewModel.fetchPokemon(args.id)
        detailsViewModel.pokemon.observe(viewLifecycleOwner) {
            binding.name.text = it.name
            binding.type.text =
                getString(R.string.types, it.types.joinToString { type -> type.type.name })
            binding.type.makeLinks(it.types.map { pokemon -> Pair(pokemon.type.name, View.OnClickListener { findNavController().navigate(
                DetailsFragmentDirections.actionNavigationDetailsToNavigationTypeDetails(pokemon.type.id)) }) })
            binding.exp.text = getString(R.string.exp,      it.baseExperience)
            binding.height.text = getString(R.string.height, it.height)
            binding.weight.text = getString(R.string.weight, it.weight)
            binding.abilities.text = getString(
                R.string.abilities,
                it.abilities.joinToString { pokemonAbility -> pokemonAbility.ability.name })
            binding.moves.text = getString(
                R.string.moves,
                it.moves.joinToString { pokemonMoves -> pokemonMoves.move.name })
            binding.image.load(Uri.parse(it.sprites.frontDefault))
            binding.baseStatsTitle.visibility = View.VISIBLE
            binding.stats.text =
                it.stats.joinToString("\n") { pokemonStat ->
                    getString(
                        R.string.stat,
                        pokemonStat.stat.name,
                        pokemonStat.baseStat,
                        pokemonStat.effort
                    )
                }
            val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
            val favouritesJson = preference.getString(FAVOURITE_KEY, null)
            val gson = Gson()
            val type = object : TypeToken<ArrayList<Pair<Int, String>>?>() {}.type
            val favourites : ArrayList<Pair<Int, String>> = gson.fromJson(favouritesJson, type) ?: arrayListOf()
            updateFavouriteButton(favourites.any { it.first == args.id })
            binding.favouriteButton.setOnClickListener {view ->
                val favourite = favourites.any { it.first == args.id }
                if (favourite) {
                    favourites.remove(favourites.first { it.first == args.id })
                } else {
                    favourites.add(Pair(args.id, it.name))
                }
                preference.edit().putString(FAVOURITE_KEY, gson.toJson(favourites)).apply()
                updateFavouriteButton(!favourite)
            }
        }
        return root
    }

    private fun updateFavouriteButton(favourite: Boolean) {
        if (favourite) {
            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.favouriteButton.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}