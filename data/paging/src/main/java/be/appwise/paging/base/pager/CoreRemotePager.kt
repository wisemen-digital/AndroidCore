package be.appwise.paging.base.pager

import androidx.paging.PagingConfig

interface CoreRemotePager {

    @Deprecated("This one gives issues when used, and is not very flexible as the real default one of Paging3", ReplaceWith("PagingConfig()", "androidx.paging.PagingConfig"))
    fun getDefaultPageConfig(
        pageSize: Int = 15,
        initialLoadSize: Int = pageSize * 3,
        enablePlaceholders: Boolean = true
    ): PagingConfig {
        return PagingConfig(
            pageSize = pageSize,
            initialLoadSize = initialLoadSize,
            enablePlaceholders = enablePlaceholders
        )
    }

    suspend fun clearAllRemoteTables()
}