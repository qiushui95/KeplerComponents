package com.kepler.components.json

import com.squareup.moshi.JsonReader
import com.squareup.moshi.JsonWriter
import son.ysy.architecture.http.json.adapter.HttpJsonAdapter
import java.lang.reflect.Type
import java.util.*

internal class BooleanJsonAdapter : HttpJsonAdapter<Boolean>(0) {

    private companion object {
        val trueStrings = arrayOf("true", "1")
    }

    override val type: Type
        get() = Boolean::class.java


    override fun fromJson(reader: JsonReader): Boolean? {
        return when (reader.peek()) {
            JsonReader.Token.STRING -> reader.nextString()
                .lowercase(Locale.ENGLISH) in trueStrings
            JsonReader.Token.NUMBER -> reader.nextInt() > 0
            JsonReader.Token.BOOLEAN -> reader.nextBoolean()
            else -> null
        }
    }

    override fun toJson(writer: JsonWriter, value: Boolean?) {
        value?.apply(writer::value)
    }
}