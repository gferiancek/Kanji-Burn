package com.gavinferiancek.ui_reviewDetail.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.review_interactors.GetReviewSubjectFromCache
import com.gavinferiancek.ui_reviewDetail.R
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class ReviewDetailViewModel
@Inject
constructor(
    private val getReviewSubjectFromCache: GetReviewSubjectFromCache,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {

    val state: MutableState<ReviewDetailState> = mutableStateOf(ReviewDetailState())

    init {
        savedStateHandle.get<Int>("subjectId")?.let { subjectId ->
            onTriggerEvent(ReviewDetailEvents.GetReviewById(subjectId, ""))
        }
    }

    fun onTriggerEvent(event: ReviewDetailEvents) {
        when (event) {
            is ReviewDetailEvents.GetReviewById -> {
                getSubject(event.id)
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
                    var srsStageName: String
                    var srsResourceId: Int
                    val reviewSubject = dataState.data

                    reviewSubject?.let {
                        srsStageName = reviewSubject.assignment.getSrsStageName()
                        srsResourceId = with(srsStageName) {
                            when {
                                contains("Apprentice") -> R.drawable.apprentice
                                contains("Guru") -> R.drawable.guru
                                contains("Master") -> R.drawable.master
                                contains("Enlightened") -> R.drawable.enlightened
                                contains("Burned") -> R.drawable.burned
                                else -> 0
                            }
                        }
                        state.value = state.value.copy(
                            reviewSubject = dataState.data,
                            srsStageName = srsStageName,
                            srsResourceId = srsResourceId
                        )
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
}