package com.example.coredemo.data.repositories

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.room.withTransaction
import be.appwise.networking.base.BaseRepository
import be.appwise.paging.base.listener.RemoteFilterMediatorListener
import be.appwise.paging.base.listener.RemoteMediatorListener
import be.appwise.paging.base.mediator.BaseFilterRemoteMediator
import be.appwise.paging.base.mediator.BaseRemoteMediator
import be.appwise.paging.base.pager.BaseFilterRemotePager
import be.appwise.paging.base.pager.BaseRemotePager
import com.example.coredemo.MyApp
import com.example.coredemo.data.database.entity.Spell
import com.example.coredemo.data.database.entity.SpellRemoteKeys
import com.example.coredemo.data.networking.DndClient
import com.example.coredemo.data.networking.models.SpellResponse
import kotlinx.coroutines.flow.Flow

object SpellRepo : BaseRepository {
    private val spellService = DndClient.getSpellsService
    private val spellDao = MyApp.database.spellDao()
    private val spellRemoteKeysDao = MyApp.database.spellRemoteKeysDao()

    @ExperimentalPagingApi
    val pagingDataAsFlow
        get() = SpellRemotePager().pagingDataAsFlow()

    class SpellFilterRemotePager : BaseFilterRemotePager<Spell, String> {
        @ExperimentalPagingApi
        override fun pagingDataAsFlow(pagingConfig: PagingConfig, query: String): Flow<PagingData<Spell>> {
            val pagingSourceFactory = { spellDao.findPaged() }
            return Pager(
                config = PagingConfig(30),
                pagingSourceFactory = pagingSourceFactory,
                remoteMediator = object : BaseFilterRemoteMediator<Spell, String>(query, PagingFilterMediatorListener()) {}
            ).flow
        }

        override suspend fun clearAllRemoteTables() {
            spellDao.deleteAllFromTable()
            spellRemoteKeysDao.clearRemoteKeys()
        }

        inner class PagingFilterMediatorListener : RemoteFilterMediatorListener<Spell, String> {
            override suspend fun getRemoteKeyById(item: Spell): SpellRemoteKeys? {
                return spellRemoteKeysDao.remoteKeyId(item.id)
            }

            override suspend fun loadPagedData(page: Int, loadType: LoadType, state: PagingState<Int, Spell>, query: String): Boolean {
                val response = spellService.getSpells(page)
                val isEndOfList = response.results.isEmpty()
                MyApp.database.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        clearAllRemoteTables()
                    }
                    val prevKey = if (page == defaultPageIndex) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val results = response.results.sortedWith(compareBy<SpellResponse> { it.level_int }.thenBy { it.slug })
                    val keys = results.map {
                        SpellRemoteKeys(itemId = "${it.level_int}_${it.slug}", prevKey = prevKey, nextKey = nextKey)
                    }
                    spellRemoteKeysDao.insertAll(keys)
                    spellDao.insertMany(results.map {
                        it.getAsEntity("${it.level_int}_${it.slug}")
                    })
                }

                return isEndOfList
            }
        }
    }

    class SpellRemotePager : BaseRemotePager<Spell> {
        @ExperimentalPagingApi
        override fun pagingDataAsFlow(pagingConfig: PagingConfig): Flow<PagingData<Spell>> {
            val pagingSourceFactory = { spellDao.findPaged() }
            return Pager(
                config = PagingConfig(30),
                pagingSourceFactory = pagingSourceFactory,
                remoteMediator = object : BaseRemoteMediator<Spell>(PagingMediatorListener()) {}
            ).flow
        }

        override suspend fun clearAllRemoteTables() {
            spellDao.deleteAllFromTable()
            spellRemoteKeysDao.clearRemoteKeys()
        }

        inner class PagingMediatorListener : RemoteMediatorListener<Spell> {
            override suspend fun getRemoteKeyById(item: Spell): SpellRemoteKeys? {
                return spellRemoteKeysDao.remoteKeyId(item.id)
            }

            override suspend fun loadPagedData(page: Int, loadType: LoadType, state: PagingState<Int, Spell>): Boolean {
                val response = spellService.getSpells(page)
                val isEndOfList = response.results.isEmpty()
                MyApp.database.withTransaction {
                    // clear all tables in the database
                    if (loadType == LoadType.REFRESH) {
                        clearAllRemoteTables()
                    }
                    val prevKey = if (page == defaultPageIndex) null else page - 1
                    val nextKey = if (isEndOfList) null else page + 1
                    val results = response.results.sortedWith(compareBy<SpellResponse> { it.level_int }.thenBy { it.slug })
                    val keys = results.map {
                        SpellRemoteKeys(itemId = "${it.level_int}_${it.slug}", prevKey = prevKey, nextKey = nextKey)
                    }
                    spellRemoteKeysDao.insertAll(keys)
                    spellDao.insertMany(results.map {
                        it.getAsEntity("${it.level_int}_${it.slug}")
                    })
                }

                return isEndOfList
            }
        }
    }
}