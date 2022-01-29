package be.appwise.paging.base.mediator


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import be.appwise.paging.base.listener.RemoteMediatorListener

@ExperimentalPagingApi
abstract class BaseRemoteMediator<T : Any>(
    val remoteMediatorListener: RemoteMediatorListener<T>
) : CoreRemoteMediator<T>(remoteMediatorListener) {

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
            val isEndOfList = remoteMediatorListener.loadPagedData(page, loadType, state)
            MediatorResult.Success(endOfPaginationReached = isEndOfList)
        } catch (exception: Exception) {
            // This will handle any exceptions that happen when the data cannot be parsed by the "doCall" function
            MediatorResult.Error(exception)
        }
    }
}