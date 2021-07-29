package be.appwise.realm

import io.realm.Realm

interface RealmRepository {
    fun getRealm(): Realm = Realm.getDefaultInstance()
}