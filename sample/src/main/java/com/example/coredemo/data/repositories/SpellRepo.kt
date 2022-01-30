package com.example.coredemo.data.repositories

import android.util.Log
import be.appwise.networking.base.BaseRepository
import com.example.coredemo.data.networking.DndClient

object SpellRepo : BaseRepository {
    private val spellService = DndClient.getSpellsService

    suspend fun getSpells() {
        spellService.getSpells().let {
            Log.d("TAG", "getSpells: $it")
        }
    }
}