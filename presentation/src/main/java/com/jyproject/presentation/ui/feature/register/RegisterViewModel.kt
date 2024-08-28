package com.jyproject.presentation.ui.feature.register

import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel @Inject constructor(

): BaseViewModel<RegisterContract.Event, RegisterContract.State, RegisterContract.Effect>() {
    override fun setInitialState() = RegisterContract.State(
        nick = null,
        registerState = RegisterState.INIT
    )

    override fun handleEvents(event: RegisterContract.Event) {
        when(event) {
            is RegisterContract.Event.NavigateToBack -> {}
            is RegisterContract.Event.NavigateToMain -> {}
            is RegisterContract.Event.OnNickCheck -> {}
        }
    }

}