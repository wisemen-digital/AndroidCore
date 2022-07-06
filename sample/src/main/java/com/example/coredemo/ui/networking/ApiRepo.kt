package com.example.coredemo.ui.networking

import be.appwise.networking.base.BaseRepository
import be.appwise.networking.handleSuccessAndReturnResponse

object ApiRepo : BaseRepository {

    private val apiService = ApiRestClient.retrofitService

    // Returning the "NetworkResponse" to the viewModel makes it possible to handle the error in the viewModels themselves.
    // As long as no error occurs we can get/manipulate/save the data
    suspend fun fetchPokemons() = apiService.fetchPokemons().handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room
    }

    suspend fun fetchSpecificPokemonOld(id: String) = doCall(apiService.fetchSpecificPokemonOld(id)).also {
        // TODO: can be used to save to Room
    }

    suspend fun fetchSpecificPokemon(id: String) = apiService.fetchSpecificPokemon(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchMovesForPokemon(id: String) = apiService.fetchMovesForPokemon(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }
}

