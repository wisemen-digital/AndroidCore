package com.example.coredemo.ui.networking

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import be.appwise.core.ui.base.BaseViewModel
import be.appwise.networking.handleResponse
import com.haroldadmin.cnradapter.NetworkResponse
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

    fun fetchSpecificPokemon(id: String) = launchAndLoad {
        val poke = handleResponse(apiRepo.fetchSpecificPokemon(id))
        val moves = handleResponse(apiRepo.fetchMovesForPokemon(id))

        delay(2000)

        poke?.let {
            it.moves = moves
            _pokemon.postValue(it)
        }
    }

    fun fetchSpecificPokemonWithDifferentHandler(id: String) = launchAndLoad {
        val poke = when (val response = apiRepo.fetchSpecificPokemon(id)) {
            is NetworkResponse.Success -> response.body
            is NetworkResponse.ServerError -> {
                setCoroutineException(Throwable(response.body?.parseErrorMessage(response.code)))
                return@launchAndLoad
            }
            is NetworkResponse.NetworkError -> {
                setCoroutineException(Throwable("Something went wrong, check if you have internet connection."))
                return@launchAndLoad
            }
            is NetworkResponse.UnknownError -> {
                setCoroutineException(Throwable("Something went wrong, please try again later."))
                return@launchAndLoad
            }
        }

        delay(2000)

        val moves = handleResponse(apiRepo.fetchMovesForPokemon(id))

        delay(2000)

        poke.let {
            it.moves = moves
            _pokemon.postValue(it)
        }
    }

    fun fetchSpecificPokemonOldWay(id: String) = launchAndLoad {
        val poke = apiRepo.fetchSpecificPokemonOld(id)
        val moves = handleResponse(apiRepo.fetchMovesForPokemon(id))

        _pokemon.postValue(poke.also { it.moves = moves })
    }
}