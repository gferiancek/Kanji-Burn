package com.gavinferiancek.core_domain

sealed class UIComponent {

    data class Dialog(
        val title: String,
        val description: String,
    ): UIComponent()

    data class SnackBar(
        val messageResId : Int,
        val action: String,
    ): UIComponent()

    data class None(
        val message: String,
    ): UIComponent()
}
