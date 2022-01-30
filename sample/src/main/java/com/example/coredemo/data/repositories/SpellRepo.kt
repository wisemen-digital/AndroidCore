package com.example.coredemo.data.repositories

import be.appwise.networking.base.BaseRepository
import com.example.coredemo.MyApp
import com.example.coredemo.data.networking.DndClient

object SpellRepo : BaseRepository {
    private val spellService = DndClient.getSpellsService
    private val spellDao = MyApp.database.spellDao()

    val spellsLive get() = spellDao.findSpellsLive()

    suspend fun getSpells() {
        spellService.getSpells().let {
            spellDao.insertMany(it.results.map { response -> response.getAsEntity })
        }
    }
}