package com.gavinferiancek.ui_login.ui

sealed class LoginEvents {

    object FetchData: LoginEvents()

    object CheckApiKey: LoginEvents()
}
