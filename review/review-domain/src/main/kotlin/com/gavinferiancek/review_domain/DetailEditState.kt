package com.gavinferiancek.review_domain

sealed class DetailEditState {

    object EditingUserSynonym: DetailEditState()

    object DeletingUserSynonym: DetailEditState()

    object EditingMeaning: DetailEditState()

    object EditingReading: DetailEditState()

    object NotEditing: DetailEditState()
}