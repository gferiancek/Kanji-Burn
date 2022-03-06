package com.gavinferiancek.ui_login.ui

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gavinferiancek.core_domain.UIComponent
import com.gavinferiancek.core_domain.state.DataState
import com.gavinferiancek.core_domain.state.ProgressBarState
import com.gavinferiancek.login_interactors.FirstDataFetch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.forEach
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
    private val firstDataFetch: FirstDataFetch,
): ViewModel() {
    val state: MutableState<LoginState> = mutableStateOf(LoginState())

    fun onTriggerEvent(events: LoginEvents) {
        when (events) {
            is LoginEvents.CheckApiKey -> {

            }
            is LoginEvents.FetchData -> {
                firstDataFetch()
            }
        }
    }

    private fun firstDataFetch() {
        firstDataFetch.execute().onEach { dataState ->
            when(dataState) {
                is DataState.Loading -> {
                    state.value = state.value.copy(
                        progressBarState = dataState.progressBarState,
                    )
                }
                is DataState.Data -> {
                    state.value = state.value.copy(hasFetchedData = true)
                }
                is DataState.Response -> {
                    Log.d("LOGIN_VIEWMODEL", (dataState.uiComponent as UIComponent.None).message)

                }
            }
        }.launchIn(viewModelScope)
    }
}