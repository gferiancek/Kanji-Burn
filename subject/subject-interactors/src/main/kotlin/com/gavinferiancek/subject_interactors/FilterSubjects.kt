package com.gavinferiancek.subject_interactors

import com.gavinferiancek.core.domain.FilterOrder
import com.gavinferiancek.subject_domain.Radical
import com.gavinferiancek.subject_domain.Subject
import com.gavinferiancek.subject_domain.SubjectFilter

class FilterSubjects {
    fun execute(
        currentSubjects: List<List<Subject>>,
        query: String,
        subjectFilter: SubjectFilter,
    ): List<List<Subject>> {
        val filteredSubjects: MutableList<MutableList<Subject>> = mutableListOf(
            currentSubjects[0].toMutableList(),
            currentSubjects[1].toMutableList(),
            currentSubjects[2].toMutableList()
        )

        filteredSubjects.forEach { list ->
            // The user can search using English (for meaning) or Japanese (for reading). Instead of
            // using a LanguageIdentifier, we can just check if there is a match in either field.
            // (Figured this was quicker and less intensive than detecting language, and then searching
            // a specific object based on language.)
            list.retainAll { subject ->
                subject.readings.any {
                    it.reading.contains(query)
                } || subject.meanings.any {
                    it.meaning.lowercase().contains(query.lowercase())
                }
            }
        }

        when (subjectFilter) {
            is SubjectFilter.Level -> {
                when(subjectFilter.order) {
                    is FilterOrder.Ascending -> {
                        filteredSubjects.forEach { list ->
                            list.sortWith(compareBy<Subject> { it.level }.thenBy { it.lessonPosition })
                        }
                    }
                    is FilterOrder.Descending -> {
                        filteredSubjects.forEach { list ->
                            list.sortWith(compareByDescending<Subject> { it.level }.thenBy { it.lessonPosition })
                        }
                    }
                }
            }
            is SubjectFilter.Reading -> {
                when(subjectFilter.order) {
                    is FilterOrder.Ascending -> {
                        filteredSubjects.forEach { list ->
                            // Radicals do not have a reading and instead return a blank string.  If
                            // a list contains Radicals, we should then default to sorting by Meaning.
                            when (list.firstOrNull()?.getPrimaryReading() == "") {
                                true -> list.sortBy { it.getPrimaryMeaning() }
                                false -> list.sortBy { it.getPrimaryReading() }
                            }
                        }
                    }
                    is FilterOrder.Descending -> {
                        filteredSubjects.forEach { list ->
                            // Radicals do not have a reading and instead return a blank string.  If
                            // a list contains Radicals, we should then default to sorting by Meaning.
                            when (list.firstOrNull()?.getPrimaryReading() == "") {
                                true -> list.sortByDescending { it.getPrimaryMeaning() }
                                false -> list.sortByDescending { it.getPrimaryReading() }
                            }
                        }
                    }
                }
            }
            is SubjectFilter.Meaning -> {
                when(subjectFilter.order) {
                    is FilterOrder.Ascending -> {
                        filteredSubjects.forEach { list ->
                            list.sortBy { it.getPrimaryMeaning() }
                        }
                    }
                    is FilterOrder.Descending -> {
                        filteredSubjects.forEach { list ->
                            list.sortByDescending { it.getPrimaryMeaning() }
                        }
                    }
                }
            }
        }
        return filteredSubjects
    }
}