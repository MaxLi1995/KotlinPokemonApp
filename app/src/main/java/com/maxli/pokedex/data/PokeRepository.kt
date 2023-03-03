package com.maxli.pokedex.data

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sargunvohra.lib.pokekotlin.model.Pokemon
import me.sargunvohra.lib.pokekotlin.model.Type
import javax.inject.Inject

class PokeRepository @Inject constructor(
    private val remoteDataSource: PokeRemoteDataSource
) {

    suspend fun getPokemonNames(page: Int): List<String> {
        return withContext(Dispatchers.IO) { remoteDataSource.getAllPokemonNames(page) }
    }

    suspend fun getPokemon(id: Int): Pokemon {
        return withContext(Dispatchers.IO) { remoteDataSource.getPokemon(id) }
    }

    suspend fun getPokemonType(page: Int): List<String> {
        return withContext(Dispatchers.IO) { remoteDataSource.getAllTypes(page) }
    }

    suspend fun getType(id: Int): Type {
        return withContext(Dispatchers.IO) { remoteDataSource.getType(id) }
    }

}