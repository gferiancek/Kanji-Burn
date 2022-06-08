package com.gavinferiancek.review_interactors.list

import com.gavinferiancek.core_domain.state.FilterOrderState
import com.gavinferiancek.core_domain.subject.Radical
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_domain.subject.getPrimaryMeaning
import com.gavinferiancek.core_domain.subject.getPrimaryReading
import com.gavinferiancek.review_domain.SubjectFilter

class FilterSubjects {
    fun execute(
        currentSubjects: List<List<Subject>>,
        query: String,
        subjectFilter: SubjectFilter,
    ): List<List<Subject>> {
        var filteredSubjects: MutableList<MutableList<Subject>> = mutableListOf()

        if (currentSubjects.isNotEmpty()) {
            filteredSubjects = mutableListOf(
                currentSubjects[0].toMutableList(),
                currentSubjects[1].toMutableList(),
                currentSubjects[2].toMutableList(),
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
                    when (subjectFilter.orderState) {
                        is FilterOrderState.Ascending -> {
                            filteredSubjects.forEach { list ->
                                list.sortWith(compareBy<Subject> { it.level }.thenBy { it.lessonPosition })
                            }
                        }
                        is FilterOrderState.Descending -> {
                            filteredSubjects.forEach { list ->
                                list.sortWith(compareByDescending<Subject> { it.level }.thenBy { it.lessonPosition })
                            }
                        }
                    }
                }
                is SubjectFilter.Reading -> {
                    when (subjectFilter.orderState) {
                        is FilterOrderState.Ascending -> {
                            filteredSubjects.forEach { list ->
                                // Radicals do not have a reading. If a list contains Radicals, we should
                                // then default to sorting by Meaning.
                                when (list.first() is Radical) {
                                    true -> list.sortBy { it.meanings.getPrimaryMeaning() }
                                    false -> list.sortBy { it.readings.getPrimaryReading() }
                                }
                            }
                        }
                        is FilterOrderState.Descending -> {
                            filteredSubjects.forEach { list ->
                                // Radicals do not have a reading and instead return a blank string.  If
                                // a list contains Radicals, we should then default to sorting by Meaning.
                                when (list.first() is Radical) {
                                    true -> list.sortByDescending { it.meanings.getPrimaryMeaning() }
                                    false -> list.sortByDescending { it.readings.getPrimaryReading() }
                                }
                            }
                        }
                    }
                }
                is SubjectFilter.Meaning -> {
                    when (subjectFilter.orderState) {
                        is FilterOrderState.Ascending -> {
                            filteredSubjects.forEach { list ->
                                list.sortBy { it.meanings.getPrimaryMeaning() }
                            }
                        }
                        is FilterOrderState.Descending -> {
                            filteredSubjects.forEach { list ->
                                list.sortByDescending { it.meanings.getPrimaryMeaning() }
                            }
                        }
                    }
                }
            }
        }
        return filteredSubjects
    }
}