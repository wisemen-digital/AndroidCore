package be.appwise.paging.base


import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator

@ExperimentalPagingApi
abstract class BaseRemoteMediator<T : Any>(val remoteMediatorListener: RemoteMediatorListener<T>) : RemoteMediator<Int, T>() {

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

    /**
     * this returns the page key or the final end of list success result
     */
    private suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, T>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: remoteMediatorListener.defaultPageIndex()
            }
            LoadType.APPEND -> {
                val remoteKeys = getLastRemoteKey(state)
                remoteKeys?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = false)
                remoteKeys.nextKey
            }
            LoadType.PREPEND -> {
                val remoteKeys = getFirstRemoteKey(state)
                //end of list condition reached
                remoteKeys?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = false)
                remoteKeys.prevKey
            }
        }
    }

    /**
     * get the first remote key inserted which had the data
     */
    private suspend fun getFirstRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { feedback -> remoteMediatorListener.getRemoteKeyById(feedback) }
    }

    /**
     * get the last remote key inserted which had the data
     */
    private suspend fun getLastRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { feedback -> remoteMediatorListener.getRemoteKeyById(feedback) }
    }

    /**
     * get the closest remote key inserted which had the data
     */
    private suspend fun getClosestRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.let { feedback -> remoteMediatorListener.getRemoteKeyById(feedback) }
        }
    }

    interface RemoteMediatorListener<T : Any> {
        suspend fun getRemoteKeyById(t: T) : BaseRemoteKeys?
        fun defaultPageIndex(): Int
        suspend fun loadPagedData(
            page: Int,
            loadType: LoadType,
            state: PagingState<Int, T>
        ): Boolean
    }

}