package be.appwise.paging.base.pager

import androidx.paging.ExperimentalPagingApi
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import kotlinx.coroutines.flow.Flow

interface BaseRemotePager<T : Any> : CoreRemotePager {

    /**
     * This functions expects to returns an [androidx.paging.Pager] object.
     *
     * From the [androidx.paging.Pager] you can get a LiveData or Flow object.
     * Because the LiveData is extracted from the Flow, we will expect a Flow
     */
    @ExperimentalPagingApi
    fun pagingDataAsFlow(pagingConfig: PagingConfig = getDefaultPageConfig()): Flow<PagingData<T>>
}
