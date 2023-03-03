package com.maxli.pokedex.ui.types

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
import com.maxli.pokedex.databinding.FragmentTypesBinding
import com.maxli.pokedex.ui.home.GradientDecoration
import com.maxli.pokedex.ui.home.HomeFragmentDirections
import com.maxli.pokedex.ui.home.PokemonNameListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypesFragment : Fragment() {

    private var _binding: FragmentTypesBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val typesViewModel =
            ViewModelProvider(this)[TypesViewModel::class.java]

        _binding = FragmentTypesBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val recyclerView: RecyclerView = binding.typesList
        recyclerView.adapter = PokemonNameListAdapter(
            mutableListOf(),
            { typesViewModel.fetchTypes() },
            { id: Int -> findNavController().navigate(TypesFragmentDirections.actionNavigationTypesToNavigationTypeDetails(id))})
        recyclerView.layoutManager = GridLayoutManager(context, 2, RecyclerView.VERTICAL, false)
        recyclerView.addItemDecoration(GradientDecoration(recyclerView))

        typesViewModel.fetchTypes()
        typesViewModel.pokemonTypes.observe(viewLifecycleOwner) {
            (recyclerView.adapter as PokemonNameListAdapter).setData(it)
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}