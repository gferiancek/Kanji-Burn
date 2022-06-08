package com.gavinferiancek.ui_reviewDetail.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.DetailEditState
import com.gavinferiancek.review_interactors.detail.GetConnectionsFromCache
import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.review_interactors.detail.UpdateStudyMaterials
import com.gavinferiancek.ui_reviewDetail.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel
@Inject
constructor(
    private val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    private val getConnectionsFromCache: GetConnectionsFromCache,
    private val updateStudyMaterials: UpdateStudyMaterials,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: MutableState<ReviewDetailState> = mutableStateOf(ReviewDetailState())

    init {
        savedStateHandle.get<Int>("subjectId")?.let { subjectId ->
            onTriggerEvent(ReviewDetailEvents.GetReviewSubject(subjectId))
        }
        state.value.reviewSubject?.let { reviewSubject ->
            onTriggerEvent(ReviewDetailEvents.GetConnections(reviewSubject.subject))
        }
    }

    fun onTriggerEvent(event: ReviewDetailEvents) {
        when (event) {
            is ReviewDetailEvents.GetReviewSubject -> {
                getReviewSubject(event.id)
            }
            is ReviewDetailEvents.GetConnections -> {
                getConnections(event.subject)
            }
            is ReviewDetailEvents.UpdateStudyMaterials -> {
                updateStudyMaterials(
                    updatedField = event.updatedField,
                )
            }
            is ReviewDetailEvents.UpdateSnackbarState -> {
                state.value = state.value.copy(snackbarState = event.uiComponentState)
            }
            is ReviewDetailEvents.UpdateDetailEditState -> {
                state.value = state.value.copy(editState = event.editState)
            }
            is ReviewDetailEvents.UpdateTextFieldValue -> {
                state.value = state.value.copy(textFieldValue = event.textFieldValue)
            }
        }
    }

    private fun getReviewSubject(id: Int) {
        getReviewSubjectFromCache.execute(id).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    dataState.data?.let { reviewSubject ->
                        state.value = state.value.copy(reviewSubject = reviewSubject)
                    }
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.None -> {
                            Log.d(
                                "SubjectDetailViewModel - getSubject",
                                (dataState.uiComponent as UIComponent.None).message
                            )
                        }
                        is UIComponent.SnackBar -> {
                            state.value = state.value.copy(
                                snackbarComponent = dataState.uiComponent,
                                snackbarState = UIComponentState.Visible
                            )
                        }
                        is UIComponent.Dialog -> {
                            // Update state to show dialog with title/error
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getConnections(subject: Subject) {
        getConnectionsFromCache.execute(subject).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    dataState.data?.let { connections ->
                        state.value = state.value.copy(connections = connections) }
                }
                is DataState.Response -> {
                    when (dataState.uiComponent) {
                        is UIComponent.None -> {
                            Log.d(
                                "SubjectDetailViewModel - getConnections",
                                (dataState.uiComponent as UIComponent.None).message
                            )
                        }
                        is UIComponent.SnackBar -> {
                            state.value = state.value.copy(
                                snackbarComponent = dataState.uiComponent,
                                snackbarState = UIComponentState.Visible
                            )
                        }
                        is UIComponent.Dialog -> {
                            // Update state to show dialog with title/error
                        }
                    }
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun updateStudyMaterials(
        updatedField: String,
    ) {
        state.value.reviewSubject?.let { reviewSubject ->
            updateStudyMaterials.execute(
                subjectId = reviewSubject.subject.id,
                studyMaterials = reviewSubject.studyMaterials,
                editState = state.value.editState,
                updatedField = updatedField,
                messageResIds = mapOf(
                    "isBlank" to R.string.updateStudyMaterials_isBlank,
                    "error" to R.string.updateStudyMaterials_error,
                )
            ).onEach { dataState ->
                when (dataState) {
                    is DataState.Loading -> {
                        state.value =
                            state.value.copy(progressBarState = dataState.progressBarState)
                    }
                    is DataState.Data -> {
                        dataState.data?.let { studyMaterials ->
                            state.value = state.value.copy(
                                reviewSubject = state.value.reviewSubject?.copy(studyMaterials = studyMaterials),
                                editState = DetailEditState.NotEditing,
                            )
                        }
                    }
                    is DataState.Response -> {
                        when (dataState.uiComponent) {
                            is UIComponent.None -> {
                                Log.d(
                                    "SubjectDetailViewModel - updateStudyMaterials",
                                    (dataState.uiComponent as UIComponent.None).message
                                )
                            }
                            is UIComponent.SnackBar -> {
                                state.value = state.value.copy(
                                    snackbarComponent = dataState.uiComponent,
                                    snackbarState = UIComponentState.Visible
                                )
                            }
                            is UIComponent.Dialog -> {}
                        }
                    }
                }
            }.catch {
                // TODO Remove Try/Catch in useCase and catch here instead
            }.launchIn(viewModelScope)
        }
    }
}