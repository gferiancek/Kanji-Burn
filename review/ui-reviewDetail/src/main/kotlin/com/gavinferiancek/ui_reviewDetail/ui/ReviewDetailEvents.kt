package com.gavinferiancek.ui_reviewDetail.ui

import androidx.compose.ui.text.input.TextFieldValue
import com.gavinferiancek.core_domain.state.UIComponentState
import com.gavinferiancek.core_domain.subject.Subject
import com.gavinferiancek.core_domain.state.StudyMaterialsEditState

sealed class ReviewDetailEvents {
    data class GetReviewSubject(
        val id: Int,
    ): ReviewDetailEvents()

    data class GetConnections(
        val subject: Subject
    ): ReviewDetailEvents()

    data class UpdateStudyMaterials(
        val updatedField: String,
        val isDeleting: Boolean = false,
    ): ReviewDetailEvents()

    data class UpdateSnackbarState(
        val uiComponentState: UIComponentState,
    ): ReviewDetailEvents()

    data class UpdateDetailEditState(
        val editState: StudyMaterialsEditState,
    ): ReviewDetailEvents()

    data class UpdateTextFieldValue(
        val textFieldValue: TextFieldValue,
    ): ReviewDetailEvents()
}
