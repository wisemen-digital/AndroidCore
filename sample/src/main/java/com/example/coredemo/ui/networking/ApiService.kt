package com.example.coredemo.ui.networking

import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/pokemons")
    suspend fun fetchPokemons(): Response<List<PokemonResponse>>

    @GET("/pokemons/{id}")
    fun fetchSpecificPokemonOld(
        @Path("id") pokemonId: String
    ): Call<PokemonResponse>

    @GET("/pokemons/{id}")
    suspend fun fetchSpecificPokemon(
        @Path("id") pokemonId: String
    ): Response<PokemonResponse>

    @GET("/pokemons/{id}/moves")
    fun fetchMovesForPokemonOld(
        @Path("id") pokemonId: String
    ): Call<List<String>>

    @GET("/pokemons/{id}/moves")
    suspend fun fetchMovesForPokemon(
        @Path("id") pokemonId: String
    ): Response<List<String>>
}