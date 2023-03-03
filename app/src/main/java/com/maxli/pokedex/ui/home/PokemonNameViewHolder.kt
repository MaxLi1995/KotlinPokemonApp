package com.maxli.pokedex.ui.home

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.maxli.pokedex.R

class PokemonNameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val name: TextView

    init {
        name = itemView.findViewById(R.id.pokemon_name)
    }
}