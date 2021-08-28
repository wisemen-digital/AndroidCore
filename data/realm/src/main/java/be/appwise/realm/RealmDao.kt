package be.appwise.realm

import io.realm.Realm
import io.realm.RealmModel
import io.realm.RealmObject

abstract class RealmDao<T : RealmModel>(var db: Realm) {
    companion object {
        const val NO_LIMIT: Long = -1L
    }

    fun copyOrUpdate(entityParam: T): T {
        var entity = entityParam

        if (db.isInTransaction) {
            entity = db.copyToRealmOrUpdate(entity)
        } else {
            db.executeTransaction { realm -> entity = realm.copyToRealmOrUpdate(entity) }
        }

        return entity
    }

    fun createOrUpdateObjectFromJson(java: Class<T>, json: String): T {
        var entity = java.newInstance()

        if (db.isInTransaction) {
            entity = db.createOrUpdateObjectFromJson(java, json)
        } else {
            db.executeTransaction { realm -> entity = realm.createOrUpdateObjectFromJson(java, json) }
        }

        return entity
    }

    fun createOrUpdateAllFromJson(java: Class<T>, json: String) {
        if (db.isInTransaction) {
            db.createOrUpdateAllFromJson(java, json)
        } else {
            db.executeTransaction { realm -> realm.createOrUpdateAllFromJson(java, json) }
        }
    }

    inline fun <reified T : RealmObject> deleteAll() {
        if (db.isInTransaction) {
            db.delete(T::class.java)
        } else {
            db.executeTransaction { realm -> realm.delete(T::class.java) }
        }
    }

    fun transaction(action: () -> Unit) {
        db.executeTransaction {
            action()
        }
    }
}