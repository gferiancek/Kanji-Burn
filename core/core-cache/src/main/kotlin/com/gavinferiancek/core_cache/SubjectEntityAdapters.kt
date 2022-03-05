package com.gavinferiancek.core_cache

import com.gavinferiancek.subject_domain.*
import com.squareup.sqldelight.ColumnAdapter

val meaningsAdapter = object: ColumnAdapter<List<Meaning>, String> {
    override fun decode(databaseValue: String) = TypeConverters.fromMeaningsJson(databaseValue)

    override fun encode(value: List<Meaning>) = TypeConverters.toJson(value)
}

val auxiliaryMeaningsAdapter = object : ColumnAdapter<List<AuxiliaryMeaning>, String> {
    override fun decode(databaseValue: String) =
        TypeConverters.fromAuxiliaryMeaningsJson(databaseValue)

    override fun encode(value: List<AuxiliaryMeaning>) = TypeConverters.toJson(value)
}

val readingsAdapter = object : ColumnAdapter<List<Reading>, String> {
    override fun decode(databaseValue: String) = TypeConverters.fromReadingsJson(databaseValue)

    override fun encode(value: List<Reading>) = TypeConverters.toJson(value)
}

val idListAdapter = object : ColumnAdapter<List<Int>, String> {
    override fun decode(databaseValue: String) = TypeConverters.fromIdListJson(databaseValue)

    override fun encode(value: List<Int>) = TypeConverters.toJson(value)
}

val partsOfSpeechAdapter = object : ColumnAdapter<List<String>, String> {
    override fun decode(databaseValue: String) = TypeConverters.fromPartsOfSpeechJson(databaseValue)

    override fun encode(value: List<String>) = TypeConverters.toJson(value)
}

val contextSentencesAdapter = object : ColumnAdapter<List<ContextSentence>, String> {
    override fun decode(databaseValue: String) =
        TypeConverters.fromContextSentencesJson(databaseValue)

    override fun encode(value: List<ContextSentence>) = TypeConverters.toJson(value)
}

val pronunciationAudiosAdapter = object : ColumnAdapter<List<PronunciationAudio>, String> {
    override fun decode(databaseValue: String) =
        TypeConverters.fromPronunciationAudiosJson(databaseValue)

    override fun encode(value: List<PronunciationAudio>) = TypeConverters.toJson(value)
}