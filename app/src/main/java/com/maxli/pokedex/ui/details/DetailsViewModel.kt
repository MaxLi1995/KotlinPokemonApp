package com.maxli.pokedex.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxli.pokedex.data.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(private val repository: PokeRepository) : ViewModel() {

    private val _pokemon = MutableLiveData<Pokemon>()
    val pokemon: LiveData<Pokemon> = _pokemon

    fun fetchPokemon(id: Int) {
        viewModelScope.launch {
            _pokemon.value = repository.getPokemon(id)
        }
    }
}