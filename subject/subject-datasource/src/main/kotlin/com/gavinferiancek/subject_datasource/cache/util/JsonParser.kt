package com.gavinferiancek.subject_datasource.cache.util

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import kotlin.reflect.KClass

interface JsonParser {
    fun <T> fromJson(json: String, type: Type): T?

    fun <T> toJson(obj: T): String?
}