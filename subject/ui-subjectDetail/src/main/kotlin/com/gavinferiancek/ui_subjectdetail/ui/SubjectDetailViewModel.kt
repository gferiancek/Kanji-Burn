package com.gavinferiancek.ui_subjectdetail.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core.domain.DataState
import com.gavinferiancek.core.domain.UIComponent
import com.gavinferiancek.subject_interactors.GetSubjectById
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class SubjectDetailViewModel
@Inject
constructor(
    private val getSubjectById: GetSubjectById,
    savedStateHandle: SavedStateHandle,
): ViewModel() {

    val state: MutableState<SubjectDetailState> = mutableStateOf(SubjectDetailState())

    init {
        savedStateHandle.get<Int>("subjectId")?.let { subjectId ->
            onTriggerEvent(SubjectDetailEvents.GetSubjectById(subjectId, ""))
        }
    }

    fun onTriggerEvent(event: SubjectDetailEvents) {
        when (event) {
            is SubjectDetailEvents.GetSubjectById -> {
                getSubject(event.id)
            }
        }
    }

    private fun getSubject(id: Int) {
        getSubjectById.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(subject = dataState.data)
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.None -> {
                            Log.d("SubjectDetailViewModel", (dataState.uiComponent as UIComponent.None).message)
                        }
                        is UIComponent.SnackBar -> {}
                        is UIComponent.Dialog -> {
                            // Update state to show dialog with title/error
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }
}