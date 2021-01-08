package be.appwise.core.data.base

import be.appwise.core.R
import be.appwise.core.core.CoreApp
import be.appwise.core.networking.Networking
import be.appwise.core.networking.base.BaseRestClient
import be.appwise.core.networking.model.PaginationObject
import com.google.gson.Gson
import com.google.gson.JsonArray
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.net.UnknownHostException

open class BaseRepository {

    suspend inline fun <reified T : BaseEntity> nextCall(nextUrl: String?, dao: BaseRoomDao<T>, call: (String) -> Call<PaginationObject>): String? =
        nextUrl?.let {
            val paginationObject = doCall(call(it))
            val type = object : TypeToken<List<T?>?>() {}.type
            dao.insertMany(Gson().fromJson(paginationObject.data, type))

            return@let paginationObject.pagination.links?.next
        }

    /**
     * Wrap your network call with this function to have a centralized way to handle response errors
     *
     * @param call Retrofit call
     * @return Type returned by the network call
     */
    suspend fun <T : Any> doCall(call: Call<T>): T {
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