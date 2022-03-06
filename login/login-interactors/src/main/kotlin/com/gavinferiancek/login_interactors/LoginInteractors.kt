package com.gavinferiancek.login_interactors

import com.gavinferiancek.login_datasource.repository.LoginRepository

data class LoginInteractors(
    val firstDataFetch: FirstDataFetch,
) {
    companion object Factory {
        fun build(repository: LoginRepository): LoginInteractors {
            return LoginInteractors(
                firstDataFetch = FirstDataFetch(
                    repository = repository,
                ),
            )
        }
    }
}