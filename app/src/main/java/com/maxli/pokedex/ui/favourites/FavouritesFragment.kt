package com.maxli.pokedex.ui.favourites

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.maxli.pokedex.databinding.FragmentFavouritesBinding
import com.maxli.pokedex.ui.common.Constants
import com.maxli.pokedex.ui.details.DetailsFragmentDirections
import com.maxli.pokedex.ui.home.GradientDecoration
import com.maxli.pokedex.ui.home.HomeFragmentDirections
import com.maxli.pokedex.ui.home.PokemonNameListAdapter
import com.maxli.pokedex.until.TextViewExtension.Companion.makeLinks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {

    private var _binding: FragmentFavouritesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private var favourites: ArrayList<Pair<Int, String>> = arrayListOf()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val favouritesViewModel =
            ViewModelProvider(this).get(FavouritesViewModel::class.java)

        _binding = FragmentFavouritesBinding.inflate(inflater, container, false)

        val recyclerView: RecyclerView = binding.favourites
        recyclerView.adapter = PokemonNameListAdapter(
            mutableListOf(),
            { },
            { id: Int -> findNavController().navigate(FavouritesFragmentDirections.actionNavigationFavouritesToNavigationDetails(
                favourites[id - 1].first))})
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GradientDecoration(recyclerView))

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        val preference = requireActivity().getPreferences(Context.MODE_PRIVATE)
        val favouritesJson = preference.getString(Constants.FAVOURITE_KEY, null)
        val gson = Gson()
        val type = object : TypeToken<ArrayList<Pair<Int, String>>?>() {}.type
        favourites = gson.fromJson(favouritesJson, type) ?: arrayListOf()
        if (favourites.isEmpty()) {
            binding.noFavourites.visibility = View.VISIBLE
            binding.favourites.visibility = View.GONE
        } else {
            binding.noFavourites.visibility = View.GONE
            binding.favourites.visibility = View.VISIBLE
            (binding.favourites.adapter as PokemonNameListAdapter).setData(favourites.toList().map { it.second })
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}