package com.gavinferiancek.core_domain.state

sealed class StudyMaterialsEditState {

    object EditingUserSynonym: StudyMaterialsEditState()

    object DeletingUserSynonym: StudyMaterialsEditState()

    object EditingMeaningNote: StudyMaterialsEditState()

    object EditingReadingNote: StudyMaterialsEditState()

    object NotEditing: StudyMaterialsEditState()
}