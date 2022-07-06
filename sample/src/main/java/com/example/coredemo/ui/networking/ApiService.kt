package com.example.coredemo.ui.networking

import com.haroldadmin.cnradapter.NetworkResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/pokemons")
    suspend fun fetchPokemons(): NetworkResponse<List<PokemonResponse>, MyApiError>

    @GET("/pokemons/{id}")
    fun fetchSpecificPokemonOld(
        @Path("id") pokemonId: String
    ): Call<PokemonResponse>

    @GET("/pokemons/{id}")
    suspend fun fetchSpecificPokemon(
        @Path("id") pokemonId: String
    ): NetworkResponse<PokemonResponse, MyApiError>

    @GET("/pokemons/{id}/moves")
    suspend fun fetchMovesForPokemon(
        @Path("id") pokemonId: String
    ): NetworkResponse<List<String>, MyApiError>
}