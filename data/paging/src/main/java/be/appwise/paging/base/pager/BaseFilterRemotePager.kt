package be.appwise.paging.base.pager

import androidx.paging.*
import kotlinx.coroutines.flow.Flow

interface BaseFilterRemotePager<T : Any, QT : Any>: CoreRemotePager {

    /**
     * This functions expects to returns an [androidx.paging.Pager] object.
     *
     * From the [androidx.paging.Pager] you can get a LiveData or Flow object.
     * Because the LiveData is extracted from the Flow, we will expect a Flow
     */
    @ExperimentalPagingApi
    fun pagingDataAsFlow(
        pagingConfig: PagingConfig = getDefaultPageConfig(),
        query: QT
    ): Flow<PagingData<T>>
}
