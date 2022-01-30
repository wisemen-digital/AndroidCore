package com.example.coredemo.data.networking.models

data class SpellResponse(
    val slug: String,
    val name: String,
    val desc: String,
    val level: String,
    val level_int: Int
)