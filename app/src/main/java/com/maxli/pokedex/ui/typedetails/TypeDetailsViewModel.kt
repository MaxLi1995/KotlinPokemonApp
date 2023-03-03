package com.maxli.pokedex.ui.typedetails

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.maxli.pokedex.data.PokeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.Type
import javax.inject.Inject

@HiltViewModel
class TypeDetailsViewModel @Inject constructor(private val repository: PokeRepository): ViewModel() {

    private val _type = MutableLiveData<Type>()
    val type: LiveData<Type> = _type

    fun fetchType(id: Int) {
        viewModelScope.launch {
            _type.value = repository.getType(id)
        }
    }
}