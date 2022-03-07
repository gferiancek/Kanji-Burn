package com.gavinferiancek.review_interactors.list

import com.gavinferiancek.core_domain.subject.Subject

class GetInnerSubjectListCounts {
    fun execute(
        subjects: List<List<Subject>>,
    ): List<Int> {
        val listCounts = mutableListOf<Int>()

        subjects.forEach { innerList ->
            listCounts.add(innerList.count())
        }
        return listCounts
    }
}