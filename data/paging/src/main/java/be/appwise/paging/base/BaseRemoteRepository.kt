package be.appwise.paging.base

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface BaseRemoteRepository<T : Any> {
    val defaultPageIndex: Int

    fun getDefaultPageConfig(pageSize: Int = 15, initialLoadSize: Int = 30, enablePlaceholders: Boolean = false): PagingConfig {
        return PagingConfig(pageSize = pageSize, initialLoadSize = initialLoadSize, enablePlaceholders = enablePlaceholders)
    }

    suspend fun getRemoteKeyById(item: T): BaseRemoteKeys?

    suspend fun loadPagedData(page: Int, loadType: LoadType): Boolean

    @ExperimentalPagingApi
    fun pagingDataLive(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<T>>

    suspend fun clearAllRemoteTables()
}
