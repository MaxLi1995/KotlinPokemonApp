package com.maxli.pokedex.ui.types

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxli.pokedex.data.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TypesViewModel @Inject constructor(private val repository: PokeRepository) : ViewModel() {

    private val _pokemonTypes = MutableLiveData<List<String>>().apply {
        value = mutableListOf()
    }
    val pokemonTypes: LiveData<List<String>> = _pokemonTypes

    private var pageCount = 0

    fun fetchTypes() {
        viewModelScope.launch {
            val list = _pokemonTypes.value?.toMutableList() ?: mutableListOf()
            list.addAll(repository.getPokemonType(pageCount))
            _pokemonTypes.value = list
            pageCount++
        }
    }
}