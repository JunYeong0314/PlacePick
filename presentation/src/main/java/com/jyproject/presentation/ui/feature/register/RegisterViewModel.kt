package com.jyproject.presentation.ui.feature.register

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.usecase.CheckNickUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val checkNickUseCase: CheckNickUseCase
): BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>() {
    override fun setInitialState() = RegisterContract.State(
        nick = null,
        registerState = RegisterState.INIT,
        checkNickResponseCode = null
    )

    override fun handleEvents(event: RegisterContract.Event) {
        when(event) {
            is RegisterContract.Event.NavigateToBack -> {}
            is RegisterContract.Event.NavigateToMain -> {}
            is RegisterContract.Event.OnNickCheck -> {
                checkNick(nick = event.nick)
            }
        }
    }

    private fun checkNick(nick: String) {
        viewModelScope.launch {
            checkNickUseCase(nick)
                .onSuccess { responseCode->
                    setState { copy(registerState = RegisterState.NICK_SUCCESS) }
                    setState { copy(checkNickResponseCode = responseCode) }
                }
                .onFailure {
                    setState { copy(registerState = RegisterState.ERROR) }
                }
        }
    }

}