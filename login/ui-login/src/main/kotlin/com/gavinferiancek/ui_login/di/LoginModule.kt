package com.gavinferiancek.ui_login.di

import com.gavinferiancek.login_interactors.FirstDataFetch
import com.gavinferiancek.login_interactors.LoginInteractors
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object LoginModule {

    @Provides
    @ViewModelScoped
    fun providesFirstDataFetch(
        interactors: LoginInteractors,
    ): FirstDataFetch {
        return interactors.firstDataFetch
    }

}