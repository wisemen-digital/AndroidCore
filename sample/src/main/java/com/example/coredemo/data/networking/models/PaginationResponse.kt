package com.example.coredemo.data.networking.models

data class PaginationResponse<T>(
    val results: T,
    val count: Int,
    val next: String?,
    val previous: String?
)