package com.gavinferiancek.ui_reviewList.ui

import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_interactors.list.FilterSubjects
import com.gavinferiancek.review_interactors.list.GetInnerSubjectListCounts
import com.gavinferiancek.review_interactors.list.GetSubjectsFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReviewListViewModel
@Inject
constructor(
    private val getSubjectsFromCache: GetSubjectsFromCache,
    private val filterSubjects: FilterSubjects,
    private val getInnerSubjectListCounts: GetInnerSubjectListCounts,
) : ViewModel() {
    val state: MutableState<ReviewListState> = mutableStateOf(ReviewListState())
    private var filterJob: Job? = null

    init {
        Log.d("ReviewListEvents", "Calling GetSubjects()")
        onTriggerEvent(ReviewListEvents.GetSubjects(""))
    }

    fun onTriggerEvent(event: ReviewListEvents) {
        when (event) {
            is ReviewListEvents.FilterSubjects -> {
                debounce { filterSubjects() }
            }
            is ReviewListEvents.GetSubjects -> {
                getSubjects()
            }
            is ReviewListEvents.UpdateSearchQuery -> {
                state.value = state.value.copy(query = event.query)
            }
            is ReviewListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState = event.uiComponentState)
            }
            is ReviewListEvents.UpdateSubjectFilter -> {
                state.value = state.value.copy(subjectFilter = event.subjectFilter)
                filterSubjects()
            }
        }
    }

    /**
     * Immediate filtering when typing a query looks kind of jarring.  We add a small delay so that
     * the filterSubjects() use case will be called AFTER they finish typing, instead of immediately
     * after every key press.
     */
    private fun debounce(action: () -> Unit) {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            delay(250)
            action()
        }
    }

    private fun filterSubjects() {
        val filteredSubjects = filterSubjects.execute(
            currentSubjects = state.value.subjects,
            query = state.value.query,
            subjectFilter = state.value.subjectFilter,
        )
        state.value = state.value.copy(
            filteredSubjects = filteredSubjects,
            filteredListCount = getInnerSubjectListCounts.execute(filteredSubjects)
        )
    }

    private fun getSubjects() {
        getSubjectsFromCache.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    dataState.data?.let { data ->
                        state.value = state.value.copy(
                            subjects = data,
                            initialListCount = getInnerSubjectListCounts.execute(data)
                        )
                        filterSubjects()
                    }
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.Dialog -> {

                        }
                        is UIComponent.SnackBar -> {

                        }
                        is UIComponent.None -> {
                            Log.d(
                                "SubjectListViewModel",
                                (dataState.uiComponent as UIComponent.None).message
                            )
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}