package be.appwise.paging.base.listener

import androidx.paging.LoadType
import androidx.paging.PagingState

interface RemoteFilterMediatorListener<T : Any, QT : Any>: CoreMediatorListener<T> {

    suspend fun loadPagedData(
        page: Int,
        loadType: LoadType,
        state: PagingState<Int, T>,
        query: QT
    ): Boolean
}
