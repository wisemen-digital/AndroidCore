package be.appwise.realm

import com.google.gson.ExclusionStrategy
import com.google.gson.FieldAttributes
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.realm.RealmObject
import retrofit2.converter.gson.GsonConverterFactory

object RealmUtils {
    fun getRealmGson(): Gson {
        val builder = GsonBuilder()
        builder.setExclusionStrategies(object : ExclusionStrategy {
            override fun shouldSkipField(f: FieldAttributes): Boolean {
                return f.declaringClass == RealmObject::class.java
            }

            override fun shouldSkipClass(clazz: Class<*>): Boolean {
                return false
            }
        })
        return builder.create()
    }

    /**
     * To use this you can easily override the 'createRetrofit()' function of AndroidCore's Networking submodule
     *
     * ```
     * override fun createRetrofit(baseUrl: String): Retrofit {
     *     return super.createRetrofit(baseUrl).newBuilder()
     *       .addConverterFactory(RealmUtils.getRealmGsonFactory())
     *       .build()
     * }
     * ```
     */
    fun getRealmGsonFactory(): GsonConverterFactory {
        return GsonConverterFactory.create(getRealmGson())
    }
}