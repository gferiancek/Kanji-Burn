package com.gavinferiancek.ui_subjectlist.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_interactors.FilterSubjects
import com.gavinferiancek.subject_interactors.GetSubjects
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SubjectListViewModel
@Inject
constructor(
    private val getSubjects: GetSubjects,
    private val filterSubjects: FilterSubjects,
): ViewModel() {
    val state: MutableState<SubjectListState> = mutableStateOf(SubjectListState())

    init {
        onTriggerEvent(SubjectListEvents.GetSubjects(""))
    }

    fun onTriggerEvent(event: SubjectListEvents) {
        when(event) {
            is SubjectListEvents.FilterSubjects -> {
                filterSubjects()
            }
            is SubjectListEvents.GetSubjects -> {
                getSubjects()
            }
            is SubjectListEvents.UpdateSearchQuery -> {
                state.value = state.value.copy(query = event.query)
            }
            is SubjectListEvents.UpdateFilterDialogState -> {
                state.value = state.value.copy(filterDialogState =  event.uiComponentState)
            }
            is SubjectListEvents.UpdateSubjectFilter -> {
                state.value = state.value.copy(subjectFilter = event.subjectFilter)
                filterSubjects()
            }
        }
    }

    private fun filterSubjects() {
        val filteredSubjects = filterSubjects.execute(
            currentSubjects = state.value.subjects,
            query = state.value.query,
            subjectFilter = state.value.subjectFilter,
        )
        state.value = state.value.copy(filteredSubjects = filteredSubjects)
    }

    private fun getSubjects() {
        getSubjects.execute().onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(subjects = dataState.data?: emptyList())
                    filterSubjects()
                }
                is DataState.Response -> {
                    when(dataState.uiComponent) {
                        is UIComponent.Dialog -> {

                        }
                        is UIComponent.SnackBar -> {

                        }
                        is UIComponent.None -> {
                            Log.d("SubjectListViewModel", (dataState.uiComponent as UIComponent.None).message)
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}