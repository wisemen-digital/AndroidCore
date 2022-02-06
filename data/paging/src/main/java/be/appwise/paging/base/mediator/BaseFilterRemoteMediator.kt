package be.appwise.paging.base.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import be.appwise.paging.base.listener.RemoteFilterMediatorListener

@ExperimentalPagingApi
abstract class BaseFilterRemoteMediator<T : Any, QT : Any>(
    val query: QT,
    val remoteFilterMediatorListener: RemoteFilterMediatorListener<T, QT>
) : CoreRemoteMediator<T>(remoteFilterMediatorListener) {

    override suspend fun load(loadType: LoadType, state: PagingState<Int, T>): MediatorResult {
        val page = when (val pageKeyData = getKeyPageData(loadType, state)) {
            is MediatorResult.Success -> {
                return pageKeyData
            }
            else -> {
                pageKeyData as Int
            }
        }

        return try {
            val isEndOfList = remoteFilterMediatorListener.loadPagedData(page, loadType, state, query)
            MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: Exception) {
            // This will handle any exceptions that happen when the data cannot be parsed by the "doCall" function
            MediatorResult.Error(exception)
        }
    }
}