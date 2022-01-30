package com.example.coredemo.data.networking.models

import com.example.coredemo.data.database.entity.Spell

data class SpellResponse(
    val slug: String,
    val name: String,
    val desc: String,
    val level: String,
    val level_int: Int
) {

    val getAsEntity get() = Spell(slug, slug, name, desc, level, level_int)
}