package com.jyproject.presentation.ui.feature.register

import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.usecase.CheckNickUseCase
import com.jyproject.domain.features.auth.usecase.RegisterUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(
    private val checkNickUseCase: CheckNickUseCase,
    private val registerUseCase: RegisterUseCase
): BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>() {
    override fun setInitialState() = RegisterContract.State(
        nick = null,
        registerState = RegisterState.INIT,
        checkNickResponseCode = null
    )

    override fun handleEvents(event: RegisterContract.Event) {
        when(event) {
            is RegisterContract.Event.NavigateToBack -> {
                setEffect { RegisterContract.Effect.Navigate.ToBack }
            }
            is RegisterContract.Event.NavigateToMain -> {
                setEffect { RegisterContract.Effect.Navigate.ToMain }
            }
            is RegisterContract.Event.OnNickCheck -> {
                checkNick(nick = event.nick)
            }
            is RegisterContract.Event.OnRegisterRequest -> {
                registerRequest(userNum = event.userNum, nick = event.nick)
            }
        }
    }

    private fun checkNick(nick: String) {
        viewModelScope.launch {
            if(nick.isNotBlank()) {
                checkNickUseCase(nick)
                    .onSuccess { responseCode->
                        setState {
                            copy(
                                registerState = RegisterState.NICK_SUCCESS,
                                checkNickResponseCode = responseCode
                            )
                        }
                    }
                    .onFailure {
                        setState { copy(registerState = RegisterState.ERROR) }
                    }
            }

            if(nick.isBlank()) setState { copy(checkNickResponseCode = null, registerState = RegisterState.NICK_BLANK) }
        }
    }

    private fun registerRequest(userNum: String, nick: String) {
        viewModelScope.launch {
            registerUseCase(userNum, nick)
                .onFailure {
                    setState { copy(registerState = RegisterState.ERROR) }
                }
                .onSuccess {
                    setState { copy(registerState = RegisterState.SUCCESS) }
                }
        }
    }

}