package com.example.coredemo.ui.networking

import be.appwise.networking.base.BaseRepository
import be.appwise.networking.handleSuccessAndReturnResponse

object ApiRepo : BaseRepository {

    private val apiService = ApiRestClient.retrofitService

    // Returning the "NetworkResponse" to the viewModel makes it possible to handle the error in the viewModels themselves.
    // As long as no error occurs we can get/manipulate/save the data
    suspend fun fetchPokemons() = doCall(apiService.fetchPokemons()).also {
        // TODO: can be used to save to Room
    }


    // <editor-fold desc="The old way of doing things">
    suspend fun fetchMovesForPokemonOld(id: String) = doCall(apiService.fetchMovesForPokemonOld(id)).also {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchSpecificPokemonOld(id: String) = doCall(apiService.fetchSpecificPokemonOld(id)).also {
        // TODO: can be used to save to Room
    }
    // </editor-fold>

    // <editor-fold desc="Working with the CallAdapterFactory response wrapper">
    suspend fun fetchSpecificPokemonFactory(id: String) = apiService.fetchSpecificPokemonFactory(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchMovesForPokemonFactory(id: String) = apiService.fetchMovesForPokemonFactory(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }
    // </editor-fold>

}

