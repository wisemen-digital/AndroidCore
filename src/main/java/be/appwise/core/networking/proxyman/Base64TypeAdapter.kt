package be.appwise.core.networking.proxyman

import android.util.Base64
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException


class Base64TypeAdapter : TypeAdapter<Data?>() {
    @Throws(IOException::class)
    override fun write(out: JsonWriter, value: Data?) {
        val outString = value?.toData()?.let { Base64.encodeToString(it.toByteArray(), Base64.NO_WRAP) }
        if (outString != null)
            out.value(outString)
        else
            out.nullValue()
    }


    override fun read(`in`: JsonReader?): Data? {
        return null
    }


}