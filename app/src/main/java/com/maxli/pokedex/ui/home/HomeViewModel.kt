package com.maxli.pokedex.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxli.pokedex.data.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repository: PokeRepository): ViewModel() {

    private val _pokemonNameList = MutableLiveData<List<String>>().apply {
        value = mutableListOf()
    }
    val pokemonNameList: LiveData<List<String>> = _pokemonNameList

    private var pageCount = 0

    fun fetchPokemons() {
        viewModelScope.launch {
            val list = _pokemonNameList.value?.toMutableList() ?: mutableListOf()
            list.addAll(repository.getPokemonNames(pageCount))
            _pokemonNameList.value = list
            pageCount++
        }
    }
}