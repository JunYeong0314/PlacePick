package com.jyproject.presentation.ui.feature.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.usecase.CheckMemberUseCase
import com.jyproject.domain.features.db.repository.UserDataRepository
import com.jyproject.domain.features.login.usecase.KakaoLoginUseCase
import com.jyproject.domain.features.login.usecase.NaverLoginUseCase
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val naverLoginUseCase: NaverLoginUseCase,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val checkMemberUseCase: CheckMemberUseCase,
    private val userDataRepository: UserDataRepository
): BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {
    override fun setInitialState() = LoginContract.State(
        userNum = null,
        loginState = LoginState.INIT
    )

    override fun handleEvents(event: LoginContract.Event) {
        when(event) {
            is LoginContract.Event.Retry -> {}
            is LoginContract.Event.KakaoLogin -> { startKakaoLogin() }
            is LoginContract.Event.NaverLogin -> { startNaverLogin(event.context) }
            is LoginContract.Event.NavigationToRegister -> {
                setEffect { LoginContract.Effect.Navigation.ToRegisterScreen(event.userNum) }
            }
            is LoginContract.Event.NavigationToMain -> {
                setEffect { LoginContract.Effect.Navigation.ToMainScreen }
            }
        }
    }

    fun startKakaoLogin() {
        setState { copy(loginState = LoginState.LOADING) }
        kakaoLoginUseCase(updateSocialToken = {}) { userNum ->
            userNum?.let {
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                startLogin(userNum = userNum)
            }
        }
        setState { copy(loginState = LoginState.INIT) }
    }

    private fun startNaverLogin(context: Context) {
        setState { copy(loginState = LoginState.LOADING) }
        naverLoginUseCase(context = context, updateSocialToken = {}) { userNum->
            userNum?.let {
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                startLogin(userNum = userNum)
            }
        }
        setState { copy(loginState = LoginState.INIT) }
    }

    fun initSetting(){
        viewModelScope.launch {
            val userNum = userDataRepository.getUserData().userNum

            if(userNum.isNotBlank()){
                setState { copy(loginState = LoginState.LOADING) }
                checkMemberUseCase(userNum)
                    .onFailure { setState { copy(loginState = LoginState.ERROR) } }
                    .onSuccess { isExist ->
                        when(isExist) {
                            true -> setState { copy(loginState = LoginState.EXIST_USER) }
                            false -> setState { copy(loginState = LoginState.FIRST_USER) }
                            else -> setState { copy(loginState = LoginState.ERROR) }
                        }
                    }
            }
        }
    }

    // 로그인을 시작 할 때 사용
    fun startLogin(userNum: String) {
        setState { copy(loginState = LoginState.LOADING) }
        viewModelScope.launch {
            checkMemberUseCase(userNum)
                .onFailure { setState { copy(loginState = LoginState.ERROR) } }
                .onSuccess { isExist ->
                    when(isExist) {
                        true -> setState { copy(loginState = LoginState.EXIST_USER) }
                        false -> setState { copy(userNum = userNum, loginState = LoginState.REGISTER) }
                        else -> setState { copy(loginState = LoginState.ERROR) }
                    }
                }
        }
    }
}