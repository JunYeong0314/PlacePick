package com.jyproject.presentation.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import com.jyproject.domain.features.login.NaverLoginUseCase
import com.jyproject.presentation.ui.login.Platform.KAKAO
import com.jyproject.presentation.ui.login.Platform.NAVER
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val naverLoginUseCase: NaverLoginUseCase
): ViewModel() {
    private val _loginFlow = MutableStateFlow<Boolean?>(null)
    val loginFlow: StateFlow<Boolean?> = _loginFlow.asStateFlow()

    fun startLogin(platform: String){
        when(platform){
            KAKAO -> {
            }
            NAVER -> {
                naverLoginUseCase { token->
                    _loginFlow.update { !token.isNullOrEmpty() }
                }
            }
        }
    }
}