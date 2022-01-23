package com.gavinferiancek.ui_subjectdetail.ui

sealed class SubjectDetailEvents {

    data class GetSubjectById(
        val id: Int,
        val apiKey: String,
    ): SubjectDetailEvents()
}
