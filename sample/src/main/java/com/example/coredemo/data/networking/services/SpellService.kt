package com.example.coredemo.data.networking.services

import com.example.coredemo.data.networking.models.PaginationResponse
import com.example.coredemo.data.networking.models.SpellResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpellService {

    @GET("spells")
    suspend fun getSpells(
        @Query("format") format: String = "json"
    ): PaginationResponse<List<SpellResponse>>
}