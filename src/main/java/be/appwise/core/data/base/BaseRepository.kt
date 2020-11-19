package be.appwise.core.data.base

import io.realm.Realm

open class BaseRepository {
    val realm: Realm = Realm.getDefaultInstance()
}