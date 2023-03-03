package com.maxli.pokedex.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.maxli.pokedex.R


class PokemonNameListAdapter(
    private var pokemonNames: MutableList<String>,
    private val nearBottomCallBack: () -> Unit,
    private val onClickCallback: (Int) -> Unit
) : RecyclerView.Adapter<PokemonNameViewHolder>() {

    fun setData(data: List<String>) {
        val diffCallback = PokemonDiffCallback(pokemonNames, data)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.pokemonNames.clear()
        this.pokemonNames.addAll(data)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonNameViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pokemon_name_item, parent, false)

        return PokemonNameViewHolder(view)
    }

    override fun onBindViewHolder(holder: PokemonNameViewHolder, position: Int) {
        holder.name.text = pokemonNames[position]
        if (position == pokemonNames.size - 1) {
            nearBottomCallBack.invoke()
        }
        holder.itemView.setOnClickListener {
            onClickCallback.invoke(position + 1) // a hack as pokemon id is +1 from position
        }
    }

    override fun getItemCount(): Int {
        return pokemonNames.size
    }
}

class PokemonDiffCallback(
    private val oldPokemonList: List<String>,
    private val newPokemonList: List<String>
) :
    DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldPokemonList.size
    }

    override fun getNewListSize(): Int {
        return newPokemonList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPokemonList[oldItemPosition] === newPokemonList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldPokemonList[oldItemPosition] == newPokemonList[newItemPosition]
    }
}