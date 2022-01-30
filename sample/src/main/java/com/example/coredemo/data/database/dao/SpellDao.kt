package com.example.coredemo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import be.appwise.room.BaseRoomDao
import com.example.coredemo.data.database.entity.Spell
import com.example.coredemo.data.database.utils.DBConstants

@Dao
abstract class SpellDao : BaseRoomDao<Spell>(DBConstants.TABLE_SPELLS) {

    @Query("SELECT * FROM ${DBConstants.TABLE_SPELLS}")
    abstract fun findSpellsLive(): LiveData<List<Spell>>
}