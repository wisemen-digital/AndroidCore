package com.example.coredemo.data.networking.services

import com.example.coredemo.data.networking.models.PaginationResponse
import com.example.coredemo.data.networking.models.SpellResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface SpellService {

    @GET("spells")
    suspend fun getSpells(
        @Query("page") page: Int,
        @Query("format") format: String = "json",
        @Query("ordering") ordering: String = "level_int,slug",
        @Query("limit") limit: Int = 30
    ): PaginationResponse<List<SpellResponse>>
}