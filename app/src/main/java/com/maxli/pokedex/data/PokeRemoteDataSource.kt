package com.maxli.pokedex.data

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import me.sargunvohra.lib.pokekotlin.client.PokeApi
import me.sargunvohra.lib.pokekotlin.client.PokeApiClient
import me.sargunvohra.lib.pokekotlin.model.*

class PokeRemoteDataSource(private val client: PokeApi) {
    companion object {
        const val PER_PAGE_LIMIT = 20
    }

    fun getAllPokemonNames(page: Int): List<String> {
        return client.getPokemonList(
            offset = page * PER_PAGE_LIMIT,
            limit = PER_PAGE_LIMIT
        ).results.map { it.name }
    }

    fun getPokemon(id: Int): Pokemon {
        return client.getPokemon(id)
    }

    fun getAllTypes(page: Int): List<String> {
        return client.getTypeList(
            offset = page * PER_PAGE_LIMIT,
            limit = PER_PAGE_LIMIT
        ).results.map { it.name }
    }

    fun getType(id: Int): Type {
        return client.getType(id)
    }

}

@Module
@InstallIn(ViewModelComponent::class)
object PokeModule {
    @Provides
    fun providePokeRemoteDataSource(
    ): PokeRemoteDataSource {
        return PokeRemoteDataSource(PokeApiClient())
    }
}