package be.appwise.paging.base.pager

import androidx.paging.PagingConfig

interface CoreRemotePager {

    fun getDefaultPageConfig(
        pageSize: Int = 15,
        initialLoadSize: Int = 30,
        enablePlaceholders: Boolean = false
    ): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            initialLoadSize = initialLoadSize,
            enablePlaceholders = enablePlaceholders
        )
    }

    suspend fun clearAllRemoteTables()
}