@file:Suppress("UNCHECKED_CAST")

package be.appwise.core.extensions.libraries

import io.realm.Realm
import io.realm.RealmObject

fun <T: RealmObject> T.copyFromRealm(): T = Realm.getDefaultInstance().copyFromRealm(this)