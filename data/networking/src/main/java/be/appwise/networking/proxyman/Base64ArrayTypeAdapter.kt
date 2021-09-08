package be.appwise.networking.proxyman

import android.util.Base64
import com.google.gson.*
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import java.io.IOException


class Base64ArrayTypeAdapter : TypeAdapter<ByteArray>() {
    override fun write(out: JsonWriter, value: ByteArray?) {
        if (value != null)
            out.value(Base64.encodeToString(value, Base64.NO_WRAP))
        else
            out.nullValue()
    }
    override fun read(`in`: JsonReader?): ByteArray? {
        return null
    }
}