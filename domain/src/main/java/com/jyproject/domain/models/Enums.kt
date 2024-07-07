package com.jyproject.domain.models
enum class KakaoLoginState {
    APP, WEB
}

enum class LoginState {
    INIT, EXIST, ERROR, LOADING, BLANK
}

enum class PlaceAddState {
    LOADING, ERROR, DUPLICATE, SUCCESS
}

enum class CommonState {
    LOADING, SUCCESS, ERROR
}