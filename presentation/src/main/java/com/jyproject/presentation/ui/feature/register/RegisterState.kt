package com.jyproject.presentation.ui.feature.register

enum class RegisterState {
    INIT, LOADING, SUCCESS,
    NICK_BLANK, NICK_SUCCESS, ERROR, NETWORK_ERROR,
}