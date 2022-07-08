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


    // <editor-fold desc="The old way of doing things">
    suspend fun fetchMovesForPokemonOld(id: String) = doCall(apiService.fetchMovesForPokemonOld(id)).also {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchSpecificPokemonOld(id: String) = doCall(apiService.fetchSpecificPokemonOld(id)).also {
        // TODO: can be used to save to Room
    }
    // </editor-fold>

    // <editor-fold desc="Working with the default Response wrapper of Retrofit itself">
    suspend fun fetchSpecificPokemon(id: String) = apiService.fetchSpecificPokemon(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchMovesForPokemon(id: String) = apiService.fetchMovesForPokemon(id).handleSuccessAndReturnResponse {
        // TODO: can be used to save to Room or do something else!!
    }
    // </editor-fold>

    // <editor-fold desc="Working with our own Custom wrapper">
    suspend fun fetchSpecificPokemonNewWrapper(id: String) = safeApiCall({ apiService.fetchSpecificPokemon(id) }) {
        // TODO: can be used to save to Room or do something else!!
    }

    suspend fun fetchMovesForPokemonNewWrapper(id: String) = safeApiCall({ apiService.fetchMovesForPokemon(id) }) {
        // TODO: can be used to save to Room or do something else!!
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

    /**
     * Can be overridden to use a different subclass of ApiError, will not be used by the CallAdapterFactory-wrapper option
     */
//    override fun parseError(response: Response<*>): ApiError? {
//        return Gson().fromJson(response.errorBody()?.string() ?: "{}", MyApiError::class.java)
//    }
}

