package com.gavinferiancek.core_cache.util

import com.google.gson.Gson
import java.lang.reflect.Type

class GsonParser(
    private val gson: Gson,
): JsonParser {
    override fun <T> fromJson(json: String, type: Type): T? {
        return gson.fromJson(json, type)
    }

    override fun <T> toJson(obj: T): String? {
        return gson.toJson(obj)
    }
}