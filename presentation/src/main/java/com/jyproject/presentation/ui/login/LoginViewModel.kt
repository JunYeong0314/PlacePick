package com.jyproject.presentation.ui.login

import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.jyproject.domain.features.auth.UserAuthUseCase
import com.jyproject.domain.features.login.KakaoLoginUseCase
import com.jyproject.domain.features.login.NaverLoginUseCase
import com.jyproject.domain.models.LoginState
import com.jyproject.domain.models.User
import com.jyproject.presentation.ui.login.Platform.KAKAO
import com.jyproject.presentation.ui.login.Platform.NAVER
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
    private val userAuthUseCase: UserAuthUseCase
): ViewModel() {
    private val _loginFlow = MutableStateFlow<LoginState>(LoginState.LOADING)
    val loginFlow: StateFlow<LoginState> = _loginFlow.asStateFlow()

    fun startKakaoLogin(){
        kakaoLoginUseCase { userId->
            userId?.let { isMember(userId) }
        }
    }

    fun startNaverLogin(context: Context){
        naverLoginUseCase(context = context) { userId->
            userId?.let { isMember(userId) }
        }
    }

    private fun isMember(userId: String){
        userAuthUseCase.invoke(userId) { loginState ->
            _loginFlow.update { LoginState.EXIST }
            /*
            TODO 추후 로그인 처리 변경
            _loginFlow.update { loginState }
            */
        }
    }
}