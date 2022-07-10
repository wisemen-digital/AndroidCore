package com.example.coredemo.ui.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.handleResponse
import kotlinx.coroutines.delay

class NetworkingViewModel : BaseViewModel() {

    private val apiRepo = ApiRepo

    init {
        fetchPokemons()
    }

    private val _pokemons = MutableLiveData<List<PokemonResponse>>(emptyList())
    val pokemons: LiveData<List<PokemonResponse>> get() = _pokemons

    private val _pokemon = MutableLiveData<PokemonResponse>()
    val pokemon: LiveData<PokemonResponse> get() = _pokemon

    private fun fetchPokemons() = launchAndLoad {
        val pokes = apiRepo.fetchPokemons()
        _pokemons.postValue(pokes)
    }

    private fun updatePokemon(poke: PokemonResponse, moves: List<String>?) {
        poke.let {
            it.moves = moves
            _pokemon.postValue(it)
        }
    }

    // Will be called for Bulbasaur, Ivysaur and Venusaur
    // The old way of doing things
    fun fetchSpecificPokemonOldWay(id: String) = launchAndLoad {
        val moves = apiRepo.fetchMovesForPokemonOld(id)
        delay(750)
        val poke = apiRepo.fetchSpecificPokemonOld(id)
        delay(750)

        _pokemon.postValue(poke.also { it.moves = moves })
    }

    // Will be called for Weedle, Kakuna, Beedrill
    // Working with the CallAdapterFactory response wrapper
    fun fetchSpecificPokemonFactory(id: String) = launchAndLoad {
        val moves = handleResponse(apiRepo.fetchMovesForPokemonFactory(id))
        delay(750)
        val poke = handleResponse(apiRepo.fetchSpecificPokemonFactory(id)) ?: PokemonResponse()
        delay(750)

        updatePokemon(poke, moves)
    }
}