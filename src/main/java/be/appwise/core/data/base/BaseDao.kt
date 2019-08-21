package be.appwise.core.data.base

import com.google.gson.JsonObject
import io.realm.Realm
import io.realm.RealmModel
import io.realm.kotlin.deleteFromRealm

abstract class BaseDao<T : RealmModel>(val db: Realm) {

    inline fun <reified T : RealmModel> Realm.findFirst(): T? {
        return where(T::class.java).findFirst()
    }

    inline fun <reified T : RealmModel> save(jsonObject: JsonObject) : T {
       return  db.createOrUpdateObjectFromJson(T::class.java,jsonObject.toString())
    }

    inline fun <reified T : RealmModel> save(t : T) : T {
        return  db.copyToRealmOrUpdate(t)
    }

    //Copy or Update - 1 object
    fun copyOrUpdate(entityParam: T): T {
        var entity = entityParam

        if (db.isInTransaction) {
            entity = db.copyToRealmOrUpdate(entity)
        } else {
            db.executeTransaction { realm -> entity = realm.copyToRealmOrUpdate(entity) }
        }

        return entity
    }

    //Copy or Update - All Objects
    fun copyOrUpdateAll(entityParam: List<T>): List<T> {
        var entity = entityParam

        if (db.isInTransaction) {
            entity = db.copyToRealmOrUpdate(entity)
        } else {
            db.executeTransaction { realm -> entity = realm.copyToRealmOrUpdate(entity) }
        }

        return entity
    }

    //Create Or Update From Json - 1 object
    fun createOrUpdateObjectFromJson(java: Class<T>, json: String): T {
        var entity = java.newInstance()

        if (db.isInTransaction) {
            entity = db.createOrUpdateObjectFromJson(java, json)
        } else {
            db.executeTransaction { realm -> entity = realm.createOrUpdateObjectFromJson(java, json) }
        }

        return entity
    }

    //Create Or Update From Json - All objects
    fun createOrUpdateAllFromJson(java: Class<T>, json: String) {
        if (db.isInTransaction) {
            db.createOrUpdateAllFromJson(java, json)
        } else {
            db.executeTransaction { realm -> realm.createOrUpdateAllFromJson(java, json) }
        }
    }

    //Delete - 1 Object
    fun delete(input: T) {

        if (db.isInTransaction) {
            input.deleteFromRealm()
        } else {
            db.executeTransaction { input.deleteFromRealm() }
        }
    }
}