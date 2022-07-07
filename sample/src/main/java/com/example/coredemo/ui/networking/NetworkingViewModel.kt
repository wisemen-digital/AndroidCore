package com.example.coredemo.ui.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.Networking
import be.appwise.networking.handleCoreResponse
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
        val pokes = handleResponse(apiRepo.fetchPokemons())
        _pokemons.postValue(pokes ?: emptyList())
    }

    private fun updatePokemon(poke: PokemonResponse, moves: List<String>?) {
        poke.let {
            it.moves = moves
            _pokemon.postValue(it)
        }
    }

    // Will be called for Bulbasaur, Ivysaur and Venusaur
    fun fetchSpecificPokemonOldWay(id: String) = launchAndLoad {
        val moves = apiRepo.fetchMovesForPokemonOld(id)
        delay(750)
        val poke = apiRepo.fetchSpecificPokemonOld(id)
        delay(750)

        _pokemon.postValue(poke.also { it.moves = moves })
    }

    // Will be called for Charmander, Charmeleon and Charizard
    fun fetchSpecificPokemon(id: String) = launchAndLoad {
        val moves = handleResponse(apiRepo.fetchMovesForPokemon(id))
        delay(750)
        val poke = handleResponse(apiRepo.fetchSpecificPokemon(id)) ?: PokemonResponse()
        delay(750)

        updatePokemon(poke, moves)
    }

    // Will be called for Squirtle, Wartortle and Blastoise
    fun fetchSpecificPokemonWithDifferentHandler(id: String) = launchAndLoad {
        val moves = handleResponse(apiRepo.fetchMovesForPokemon(id))

        delay(750)

        val response = apiRepo.fetchSpecificPokemon(id)
        val poke = when {
            response.isSuccessful -> response.body()
            else -> {
                setCoroutineException(Throwable(Networking.parseError(response).parseErrorMessage(response.code())))
                null
            }
        } ?: PokemonResponse()

        delay(750)

        updatePokemon(poke, moves)
    }

    fun fetchSpecificPokemonWithNewWrapper(id: String) = launchAndLoad {
        val moves = handleCoreResponse(apiRepo.fetchMovesForPokemonNewWrapper(id))
        delay(750)
        val poke = handleCoreResponse(apiRepo.fetchSpecificPokemonNewWrapper(id)) ?: PokemonResponse()

        delay(750)

        updatePokemon(poke, moves)
    }
}