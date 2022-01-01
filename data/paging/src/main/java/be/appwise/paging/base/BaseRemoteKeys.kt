package be.appwise.paging.base

abstract class BaseRemoteKeys {
    abstract val itemId: Any
    abstract val nextKey: Int?
    abstract val prevKey: Int?
}