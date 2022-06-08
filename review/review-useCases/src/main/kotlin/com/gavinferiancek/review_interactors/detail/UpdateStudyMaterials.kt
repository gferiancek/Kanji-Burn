package com.gavinferiancek.review_interactors.detail

import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.studymaterials.StudyMaterials
import com.gavinferiancek.core_network.studymaterials_endpoint.StudyMaterialsService
import com.gavinferiancek.review_data.cache.ReviewCache
import com.gavinferiancek.review_domain.DetailEditState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateStudyMaterials(
    val cache: ReviewCache,
    val service: StudyMaterialsService,
) {
    fun execute(
        subjectId: Int,
        studyMaterials: StudyMaterials,
        editState: DetailEditState,
        updatedField: String,
        messageResIds: Map<String, Int>
    ): Flow<DataState<StudyMaterials>> = flow {
        try {
            if (updatedField.isBlank() && editState is DetailEditState.EditingUserSynonym) {
                emit(
                    DataState.Response(
                        uiComponent = UIComponent.SnackBar(
                            messageResId = messageResIds.getValue("isBlank"),
                            action = "OK",
                        )
                    )
                )
                return@flow
            }
            val newStudyMaterials = when (editState) {
                is DetailEditState.EditingUserSynonym -> studyMaterials.copy(
                    meaningSynonyms = studyMaterials.meaningSynonyms.plus(
                        updatedField
                    )
                )
                is DetailEditState.DeletingUserSynonym -> studyMaterials.copy(
                    meaningSynonyms = studyMaterials.meaningSynonyms.minus(
                        updatedField
                    )
                )
                is DetailEditState.EditingMeaning -> studyMaterials.copy(meaningNote = updatedField)
                is DetailEditState.EditingReading -> studyMaterials.copy(readingNote = updatedField)
                else -> studyMaterials
            }
            val studyMaterialsEntity =
                if (cache.getStudyMaterials(subjectId.toLong()) != null) {
                    service.updateStudyMaterials(
                        studyMaterials = newStudyMaterials,
                    )
                } else service.createStudyMaterials(newStudyMaterials)
            cache.insertStudyMaterials(studyMaterialsEntity)
            emit(DataState.Data(data = cache.getStudyMaterials(subjectId.toLong())))

        } catch (e: Exception) {
            emit(
                DataState.Response(
                    uiComponent = UIComponent.None(e.message ?: "Unknown")
                )
            )
            emit(
                DataState.Response(
                    uiComponent = UIComponent.SnackBar(
                        messageResId = messageResIds.getValue("error"),
                        action = "OK"
                    )
                )
            )
        }
    }
}
