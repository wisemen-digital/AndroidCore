package be.appwise.core.data.base

import be.appwise.core.R
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import io.realm.Realm
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.UnknownHostException

open class BaseRepository {
    val realm: Realm = Realm.getDefaultInstance()

    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    protected suspend fun <T : Any> doCall(call: Call<T>): T {
        return try {
            withContext(Dispatchers.IO) {
                val response = call.execute()
                if (response.isSuccessful) {
                    response.body()!!
                } else {
                    throw Exception(Networking.parseError(response).message)
                }
            }
        } catch (ex: UnknownHostException) {
            throw Exception(CoreApp.getContext().getString(R.string.internet_connection_error))
        }
    }
}