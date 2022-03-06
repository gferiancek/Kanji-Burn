package com.gavinferiancek.ui_reviewDetail.ui

import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.review_domain.model.ReviewSubject
import com.gavinferiancek.ui_reviewDetail.R

data class ReviewDetailState(
    val progressBarState: ProgressBarState = ProgressBarState.Idle,
    val reviewSubject: ReviewSubject? = null,
    val srsStageName: String = "",
    val srsResourceId: Int = R.drawable.apprentice,
)
