package com.gavinferiancek.ui_reviewList.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.review_interactors.FilterSubjects
import com.gavinferiancek.review_interactors.GetSubjectsFromCache
import dagger.hilt.android.lifecycle.HiltViewModel
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
) : ViewModel() {
    val state: MutableState<ReviewListState> = mutableStateOf(ReviewListState())
    private var filterJob: Job? = null

    init {
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
            is ReviewListEvents.UpdateReviewFilter -> {
                state.value = state.value.copy(subjectFilter = event.subjectFilter)
                filterSubjects()
            }
            is ReviewListEvents.UpdateTabData -> {
                state.value = state.value.copy(tabData = event.tabData)
            }
        }
    }

    /**
     * Immediate filtering when typing a query looks kind of jarring.  We add a small debounce so that
     * the filterSubjects() use case will be called AFTER they finish typing, instead of after every
     * key press.
     */
    private fun debounce(action: () -> Unit) {
        filterJob?.cancel()
        filterJob = viewModelScope.launch {
            delay(300)
            action()
        }
    }

    private fun filterSubjects() {
        val filteredSubjects = filterSubjects.execute(
            currentSubjects = state.value.subjects,
            query = state.value.query,
            subjectFilter = state.value.subjectFilter,
        )
        if (state.value.query.isNotBlank()) {
            onTriggerEvent(
                ReviewListEvents.UpdateTabData(
                    tabData = listOf(
                        "Radical\n(${filteredSubjects[0].count()})",
                        "Kanji\n(${filteredSubjects[1].count()})",
                        "Vocabulary\n(${filteredSubjects[2].count()})",
                    )
                )
            )
        } else {
            onTriggerEvent(
                ReviewListEvents.UpdateTabData(
                    tabData = listOf(
                        "Radical",
                        "Kanji",
                        "Vocabulary",
                    )
                )
            )
        }
        state.value = state.value.copy(filteredSubjects = filteredSubjects)
    }

    private fun getSubjects() {
        getSubjectsFromCache.execute().onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(subjects = dataState.data ?: emptyList())
                    filterSubjects()
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