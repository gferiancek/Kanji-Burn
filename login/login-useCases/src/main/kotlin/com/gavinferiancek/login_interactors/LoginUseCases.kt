package com.gavinferiancek.login_interactors

import com.gavinferiancek.login_datasource.repository.LoginRepository

data class LoginUseCases(
    val firstDataFetch: FirstDataFetch,
) {
    companion object Factory {
        fun build(repository: LoginRepository): LoginUseCases {
            return LoginUseCases(
                firstDataFetch = FirstDataFetch(
                    repository = repository,
                ),
            )
        }
    }
}