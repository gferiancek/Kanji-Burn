package com.gavinferiancek.ui_reviewDetail.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_interactors.detail.GenerateSrsStageData
import com.gavinferiancek.review_interactors.detail.GetConnectionsFromCache
import com.gavinferiancek.review_interactors.detail.GetReviewSubjectFromCache
import com.gavinferiancek.core_ui.SrsStageData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel
@Inject
constructor(
    private val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    private val getConnectionsFromCache: GetConnectionsFromCache,
    private val generateSrsStageData: GenerateSrsStageData,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: MutableState<ReviewDetailState> = mutableStateOf(ReviewDetailState())

    init {
        savedStateHandle.get<Int>("subjectId")?.let { subjectId ->
            onTriggerEvent(ReviewDetailEvents.GetReviewSubject(subjectId))
        }
        state.value.reviewSubject?.let { reviewSubject ->
            onTriggerEvent(ReviewDetailEvents.GetConnections(reviewSubject.subject))
            getSrsStageData(
                srsStage = reviewSubject.assignment.srsStage,
                unlocked = reviewSubject.subject.unlocked,
                notStarted = reviewSubject.assignment.startedAt == null,
            )
        }
    }

    fun onTriggerEvent(event: ReviewDetailEvents) {
        when (event) {
            is ReviewDetailEvents.GetReviewSubject -> {
                getSubject(event.id)
            }
            is ReviewDetailEvents.GetConnections -> {
                getConnections(event.subject)
            }
            is ReviewDetailEvents.UpdateStudyMaterials -> {
                //updateStudyMaterials(event.studyMaterials)
            }
        }
    }

    private fun getSubject(id: Int) {
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
                                "SubjectDetailViewModel",
                                (dataState.uiComponent as UIComponent.None).message
                            )
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

    private fun getConnections(subject: Subject) {
        getConnectionsFromCache.execute(subject).onEach { dataState ->
            when (dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(progressBarState = dataState.progressBarState)
                }
                is DataState.Data -> {
                    state.value = state.value.copy(connections = dataState.data)
                }
                is DataState.Response -> {
                    // will do soon
                }
            }
        }.launchIn(viewModelScope)
    }

    private fun getSrsStageData(
        srsStage: Int,
        unlocked: Boolean,
        notStarted: Boolean,
    ) {
        val srsStageData = when (srsStage) {
            0 -> {
                if (unlocked && notStarted) SrsStageData.Initiate() else SrsStageData.Unknown()
            }
            1 -> SrsStageData.ApprenticeI()
            2 -> SrsStageData.ApprenticeII()
            3 -> SrsStageData.ApprenticeIII()
            4 -> SrsStageData.ApprenticeIV()
            5 -> SrsStageData.GuruI()
            6 -> SrsStageData.GuruII()
            7 -> SrsStageData.Master()
            8 -> SrsStageData.Enlightened()
            9 -> SrsStageData.Burned()
            else -> SrsStageData.Unknown()
        }
        state.value = state.value.copy(srsStageData = srsStageData)
    }
}