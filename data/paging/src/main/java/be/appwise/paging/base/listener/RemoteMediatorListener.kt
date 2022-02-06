package be.appwise.paging.base.listener

import androidx.paging.LoadType
import androidx.paging.PagingState

interface RemoteMediatorListener<T : Any>: CoreMediatorListener<T> {

    suspend fun loadPagedData(
        page: Int,
        loadType: LoadType,
        state: PagingState<Int, T>
    ): Boolean
}
