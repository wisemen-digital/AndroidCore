package com.example.coredemo.data.database.dao

import androidx.lifecycle.LiveData
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Query
import be.appwise.paging.base.BaseRemoteKeysDao
import be.appwise.room.BaseRoomDao
import com.example.coredemo.data.database.entity.Spell
import com.example.coredemo.data.database.entity.SpellRemoteKeys
import com.example.coredemo.data.database.utils.DBConstants

@Dao
abstract class SpellDao : BaseRoomDao<Spell>(DBConstants.TABLE_SPELLS) {

    @Query("SELECT * FROM ${DBConstants.TABLE_SPELLS} ORDER BY level_int ASC, name ASC")
    abstract fun findSpellsLive(): LiveData<List<Spell>>

    @Query("SELECT * FROM ${DBConstants.TABLE_SPELLS} ORDER BY level_int ASC, slug ASC")
    abstract fun findPaged(): PagingSource<Int, Spell>
}

@Dao
abstract class SpellRemoteKeysDao: BaseRemoteKeysDao<SpellRemoteKeys>(DBConstants.TABLE_SPELLS_REMOTE_KEYS)