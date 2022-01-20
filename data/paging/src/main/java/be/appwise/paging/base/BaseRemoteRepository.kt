package be.appwise.paging.base

import androidx.paging.*
import kotlinx.coroutines.flow.Flow

interface BaseRemoteRepository<T : Any> {
    val defaultPageIndex: Int

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

    suspend fun getRemoteKeyById(item: T): BaseRemoteKeys?

    /**
     * This functions expects to returns an [androidx.paging.Pager] object. This can be build by doing this
     *
     * ```
     * return Pager(
     *     config = pagingConfig,
     *     pagingSourceFactory = pagingSourceFactory,
     *     remoteMediator = object : BaseRemoteMediator<NewsItemWithUser>() {
     *         override val repository: BaseRemoteRepository<NewsItemWithUser>
     *             get() = this@NewsRepository
     *     }
     * ).flow
     * ```
     *
     * From the [androidx.paging.Pager] you can get a LiveData or Flow object.
     * Because the LiveData is extracted from the Flow, we will expect a Flow
     */
    @ExperimentalPagingApi
    fun pagingDataAsFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<T>>

    suspend fun clearAllRemoteTables()
}
