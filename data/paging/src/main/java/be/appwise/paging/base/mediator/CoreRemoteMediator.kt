package be.appwise.paging.base.mediator

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import be.appwise.paging.base.BaseRemoteKeys
import be.appwise.paging.base.listener.CoreMediatorListener

@ExperimentalPagingApi
abstract class CoreRemoteMediator<T : Any>(
    val listener: CoreMediatorListener<T>
) : RemoteMediator<Int /*TODO: check if Int can be replaced by Any*/, T>() {

    /**
     * This returns the page key or the final end of list success result.
     *
     * Can be overridden to provide your project with a specific implementation.
     */
    open suspend fun getKeyPageData(loadType: LoadType, state: PagingState<Int, T>): Any? {
        return when (loadType) {
            LoadType.REFRESH -> {
                val remoteKeys = getClosestRemoteKey(state)
                remoteKeys?.nextKey?.minus(1) ?: listener.defaultPageIndex
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
     * Get the first remote key inserted which had the data
     *
     * Can be overridden to provide your project with a specific implementation.
     */
    open suspend fun getFirstRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.pages
            .firstOrNull { it.data.isNotEmpty() }
            ?.data?.firstOrNull()
            ?.let { item -> listener.getRemoteKeyById(item) }
    }

    /**
     * Get the last remote key inserted which had the data
     *
     * Can be overridden to provide your project with a specific implementation.
     */
    open suspend fun getLastRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.pages
            .lastOrNull { it.data.isNotEmpty() }
            ?.data?.lastOrNull()
            ?.let { item -> listener.getRemoteKeyById(item) }
    }

    /**
     * Get the closest remote key inserted which had the data
     *
     * Can be overridden to provide your project with a specific implementation.
     */
    open suspend fun getClosestRemoteKey(state: PagingState<Int, T>): BaseRemoteKeys? {
        return state.anchorPosition?.let { position ->
            state.closestItemToPosition(position)
                ?.let { item -> listener.getRemoteKeyById(item) }
        }
    }
}