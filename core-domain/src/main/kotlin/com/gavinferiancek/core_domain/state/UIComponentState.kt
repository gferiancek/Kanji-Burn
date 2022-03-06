package com.gavinferiancek.core_domain.state

sealed class UIComponentState {

    object Hidden: UIComponentState()

    object Visible: UIComponentState()
}
