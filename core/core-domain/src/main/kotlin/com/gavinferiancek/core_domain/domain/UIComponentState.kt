package com.gavinferiancek.core.domain

sealed class UIComponentState {

    object Hidden: UIComponentState()

    object Visible: UIComponentState()
}
