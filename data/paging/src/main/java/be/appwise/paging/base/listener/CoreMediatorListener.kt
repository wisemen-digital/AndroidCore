package be.appwise.paging.base.listener

import be.appwise.paging.base.BaseRemoteKeys

interface CoreMediatorListener<T> {

    suspend fun getRemoteKeyById(item: T): BaseRemoteKeys?

    /**
     * When overriding, don't forget to use the explicit `getter`
     */
    val defaultPageIndex: Int get() = 1
}