package com.jyproject.presentation.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.usecase.CheckMemberUseCase
import com.jyproject.domain.features.auth.usecase.SignUpUseCase
import com.jyproject.domain.features.db.repository.UserDataRepository
import com.jyproject.domain.features.login.usecase.KakaoLoginUseCase
import com.jyproject.domain.features.login.usecase.NaverLoginUseCase
import com.jyproject.domain.models.LoginState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val naverLoginUseCase: NaverLoginUseCase,
    private val kakaoLoginUseCase: KakaoLoginUseCase,
    private val signUpUseCase: SignUpUseCase,
    private val checkMemberUseCase: CheckMemberUseCase,
    private val userDataRepository: UserDataRepository
): ViewModel() {
    private val _loginFlow = MutableStateFlow<LoginState>(LoginState.BLANK)
    val loginFlow: StateFlow<LoginState> = _loginFlow.asStateFlow()

    private var userNumber = ""

    init {
        viewModelScope.launch {
            if(userDataRepository.getUserData().userNum.isNotBlank()){
                _loginFlow.update { LoginState.LOADING }
                isMember(userDataRepository.getUserData().userNum)
            }
        }
    }

    fun startKakaoLogin(){
        _loginFlow.update { LoginState.LOADING }
        kakaoLoginUseCase(updateSocialToken = {}) { userNum->
            userNum?.let {
                isMember(userNum)
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                userNumber = userNum
            }
        }
    }

    fun startNaverLogin(context: Context){
        _loginFlow.update { LoginState.LOADING }
        naverLoginUseCase(context = context, updateSocialToken = {}) { userNum->
            userNum?.let {
                isMember(userNum)
                viewModelScope.launch { userDataRepository.setUserData("num", userNum) }
                userNumber = userNum
            }
        }
    }

    fun signUp() {
        viewModelScope.launch {
            signUpUseCase(userNumber)
                .onFailure { _loginFlow.value = LoginState.ERROR }
                .onSuccess { token->
                    token?.let { userDataRepository.setUserData("token", token) }
                    _loginFlow.value = LoginState.EXIST
                }
        }
    }

    private fun isMember(userNum: String){
        viewModelScope.launch {
            checkMemberUseCase(userNum)
                .onFailure { _loginFlow.update { LoginState.ERROR } }
                .onSuccess { isExist->
                    when(isExist){
                        true -> _loginFlow.update { LoginState.EXIST }
                        false -> _loginFlow.update { LoginState.INIT }
                        else -> _loginFlow.update { LoginState.ERROR }
                    }
                }
        }
    }

}