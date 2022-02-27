package com.gavinferiancek.subject_datasource.cache

import com.gavinferiancek.subject_datasource.cache.util.GsonParser
import com.gavinferiancek.subject_datasource.cache.util.JsonParser
import com.gavinferiancek.subject_domain.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object TypeConverters {

    private val jsonParser: JsonParser = GsonParser(Gson())

    /**
     * The type isn't nearly as important when converting to JSON so we can use a generic.  We will
     * need a separate function for each object we wish to convert JSON to though.  I tried using a
     * generic function and passing in the desired type, but couldn't get it to work.
     */
    fun <T> toJson(list: List<T>): String {
        return jsonParser.toJson(
            obj = list
        ) ?: "[]"
    }

    fun fromMeaningsJson(json: String): List<Meaning> {
        return jsonParser.fromJson<List<Meaning>>(
            json,
            object: TypeToken<List<Meaning>>(){}.type
        ) ?: emptyList()
    }

    fun fromAuxiliaryMeaningsJson(json: String): List<AuxiliaryMeaning> {
        return jsonParser.fromJson<List<AuxiliaryMeaning>>(
            json,
            object: TypeToken<List<AuxiliaryMeaning>>(){}.type
        ) ?: emptyList()
    }

    fun fromReadingsJson(json: String): List<Reading> {
        return jsonParser.fromJson<List<Reading>>(
            json,
            object: TypeToken<List<Reading>>(){}.type
        ) ?: emptyList()
    }

    fun fromIdListJson(json: String): List<Int> {
        return jsonParser.fromJson<List<Int>>(
            json,
            object: TypeToken<List<Int>>(){}.type
        ) ?: emptyList()
    }

    fun fromPartsOfSpeechJson(json: String): List<String> {
        return jsonParser.fromJson<List<String>>(
            json,
            object: TypeToken<List<String>>(){}.type
        ) ?: emptyList()
    }

    fun fromContextSentencesJson(json: String): List<ContextSentence> {
        return jsonParser.fromJson<List<ContextSentence>>(
            json,
            object: TypeToken<List<ContextSentence>>(){}.type
        ) ?: emptyList()
    }
    fun fromPronunciationAudiosJson(json: String): List<PronunciationAudio> {
        return jsonParser.fromJson<List<PronunciationAudio>>(
            json,
            object: TypeToken<List<PronunciationAudio>>(){}.type
        ) ?: emptyList()
    }
}