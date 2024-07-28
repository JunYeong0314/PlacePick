package com.jyproject.presentation.ui.feature.login

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.usecase.CheckMemberUseCase
import com.jyproject.domain.features.auth.usecase.SignUpUseCase
import com.jyproject.domain.features.db.repository.UserDataRepository
import com.jyproject.domain.features.login.usecase.KakaoLoginUseCase
import com.jyproject.domain.features.login.usecase.NaverLoginUseCase
import com.jyproject.domain.models.LoginState
import com.jyproject.presentation.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val naverLoginUseCase: NaverLoginUseCase,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val checkMemberUseCase: CheckMemberUseCase,
    private val userDataRepository: UserDataRepository
): BaseViewModel<LoginContract.Event, LoginContract.State, LoginContract.Effect>() {
    private var userNumber = "" // 회원가입 하는 경우 사용

    override fun setInitialState() = LoginContract.State(
        loginState = LoginState.BLANK
    )

    override fun handleEvents(event: LoginContract.Event) {
        when(event) {
            is LoginContract.Event.NavigationToMain -> setEffect { LoginContract.Effect.Navigation.ToMainScreen }
            is LoginContract.Event.Retry -> {}
            is LoginContract.Event.KakaoLogin -> { startKakaoLogin() }
            is LoginContract.Event.NaverLogin -> { startNaverLogin(event.context) }
            is LoginContract.Event.SignUp -> { signUp() }
        }
    }

    fun startKakaoLogin() {
        setState { copy(loginState = LoginState.LOADING) }
        kakaoLoginUseCase(updateSocialToken = {}) { userNum ->
            userNum?.let {
                isMember(userNum)
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                userNumber = userNum
            }
        }
    }

    fun startNaverLogin(context: Context) {
        setState { copy(loginState = LoginState.LOADING) }
        naverLoginUseCase(context = context, updateSocialToken = {}) { userNum->
            userNum?.let {
                isMember(userNum)
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                userNumber = userNum
            }
        }
    }

    private fun signUp() {
        viewModelScope.launch {
            signUpUseCase(userNumber)
                .onFailure { setState { copy(loginState = LoginState.ERROR) } }
                .onSuccess { token->
                    token?.let { userDataRepository.setUserData("token", token) }
                    setState { copy(loginState = LoginState.EXIST) }
                }
        }
    }

    fun initSetting(){
        viewModelScope.launch {
            if(userDataRepository.getUserData().userNum.isNotBlank()){
                setState { copy(loginState = LoginState.LOADING) }
                isMember(userDataRepository.getUserData().userNum)
            }
        }
    }

    private fun isMember(userNum: String){
        viewModelScope.launch {
            checkMemberUseCase(userNum)
                .onFailure { setState { copy(loginState = LoginState.ERROR) } }
                .onSuccess { isExist ->
                    when(isExist) {
                        true -> setState { copy(loginState = LoginState.EXIST) }
                        false -> setState { copy(loginState = LoginState.INIT) }
                        else -> setState { copy(loginState = LoginState.ERROR) }
                    }
                }
        }
    }
}