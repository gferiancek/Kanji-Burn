package com.gavinferiancek.core_cache.model

import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.corecache.cache.StudyMaterialsEntity

fun StudyMaterialsEntity.toStudyMaterials(): StudyMaterials {
    return StudyMaterials(
        id = studyMaterialsId.toInt(),
        subjectId = subjectId.toInt(),
        meaningNote = meaningNote,
        readingNote = readingNote,
        meaningSynonyms = meaningSynonyms,
    )
}