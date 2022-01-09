package com.gavinferiancek.ui_subjectlist.ui

sealed class SubjectListEvents {

    data class GetSubjects(
        val apiKey: String,
    ): SubjectListEvents()
}
