package com.example.coredemo.ui.networking

import be.appwise.networking.BaseResponse
import be.appwise.networking.responseAdapter.NetworkResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("/pokemons")
    fun fetchPokemons(): Call<List<PokemonResponse>>

    @GET("/pokemons/{id}")
    fun fetchSpecificPokemonOld(
        @Path("id") pokemonId: String
    ): Call<PokemonResponse>

    @GET("/pokemons/{id}/moves")
    fun fetchMovesForPokemonOld(
        @Path("id") pokemonId: String
    ): Call<List<String>>

    @GET("/pokemons/{id}")
    suspend fun fetchSpecificPokemonFactory(
        @Path("id") pokemonId: String
    ): BaseResponse<PokemonResponse>

    @GET("/pokemons/{id}/moves")
    suspend fun fetchMovesForPokemonFactory(
        @Path("id") pokemonId: String
    ): NetworkResponse<List<String>, MyApiError>
}