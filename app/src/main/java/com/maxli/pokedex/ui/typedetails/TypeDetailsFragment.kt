package com.maxli.pokedex.ui.typedetails

import android.net.Uri
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import coil.load
import com.maxli.pokedex.R
import com.maxli.pokedex.databinding.FragmentDetailsBinding
import com.maxli.pokedex.databinding.FragmentTypeDetailsBinding
import com.maxli.pokedex.ui.details.DetailsFragmentArgs
import com.maxli.pokedex.ui.details.DetailsViewModel
import com.maxli.pokedex.ui.home.HomeFragmentDirections
import com.maxli.pokedex.until.TextViewExtension.Companion.makeLinks
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TypeDetailsFragment : Fragment() {

    private var _binding: FragmentTypeDetailsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val typeDetailsViewModel =
            ViewModelProvider(this)[TypeDetailsViewModel::class.java]

        _binding = FragmentTypeDetailsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        typeDetailsViewModel.fetchType(args.id)
        typeDetailsViewModel.type.observe(viewLifecycleOwner) {
            binding.typeName.text = it.name
            if (it.damageRelations.doubleDamageTo.isEmpty()){
                binding.effective.visibility = View.GONE
            } else {
                binding.effective.visibility = View.VISIBLE
                binding.effective.text =
                    getString(
                        R.string.effective,
                        it.damageRelations.doubleDamageTo.joinToString { type -> type.name })
            }
            if (it.damageRelations.doubleDamageFrom.isEmpty()){
                binding.ineffective.visibility = View.GONE
            } else {
                binding.ineffective.visibility = View.VISIBLE
                binding.ineffective.text =
                    getString(
                        R.string.ineffective,
                        it.damageRelations.doubleDamageFrom.joinToString { type -> type.name })
            }
            if (it.damageRelations.noDamageFrom.isEmpty()){
                binding.noEffectFrom.visibility = View.GONE
            } else {
                binding.noEffectFrom.visibility = View.VISIBLE
                binding.noEffectFrom.text =
                    getString(
                        R.string.no_damage_from,
                        it.damageRelations.noDamageFrom.joinToString { type -> type.name })
            }
            if (it.damageRelations.noDamageTo.isEmpty()){
                binding.noEffectTo.visibility = View.GONE
            } else {
                binding.noEffectTo.visibility = View.VISIBLE
                binding.noEffectTo.text =
                    getString(
                        R.string.no_damage_to,
                        it.damageRelations.noDamageTo.joinToString { type -> type.name })
            }
            binding.typeMoves.text = getString(
                    R.string.type_move_list, it.moves.joinToString { type -> type.name })
            binding.typePokemon.text = getString(
                R.string.type_pokemon_list, it.pokemon.joinToString { type -> type.pokemon.name })
            binding.typePokemon.makeLinks(it.pokemon.map { pokemon -> Pair(pokemon.pokemon.name, View.OnClickListener { findNavController().navigate(
                TypeDetailsFragmentDirections.actionNavigationTypeDetailsToNavigationDetails(pokemon.pokemon.id)) }) })
        }
        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}