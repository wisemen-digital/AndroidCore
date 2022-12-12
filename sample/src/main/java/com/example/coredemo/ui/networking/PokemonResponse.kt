package com.example.coredemo.ui.networking

data class PokemonResponse(
    val id: String = "",
    val name: String = "",
    val type_1: String? = null,
    val icon: String = "",
    var moves: List<String>? = null
) {

    val parseSnackbarMessage get() = "$name: ${moves?.joinToString(", ") ?: "empty"}"
}