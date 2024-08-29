package com.jyproject.presentation.ui.feature.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.jyproject.domain.models.Platform.KAKAO
import com.jyproject.domain.models.Platform.NAVER
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.login.composable.LoadingDialog
import com.jyproject.presentation.ui.feature.login.composable.LoginBox
import kotlinx.coroutines.flow.Flow

@Composable
fun LoginScreen(
    context: Context,
    state: LoginContract.State,
    effectFlow: Flow<LoginContract.Effect>?,
    onEventSend: (event: LoginContract.Event) -> Unit,
    onEffectSend: (effect: LoginContract.Effect.Navigation) -> Unit
) {
    var isLoading by remember { mutableStateOf(false) }
    val snackBarHostState = remember { SnackbarHostState() }

    LaunchedEffect(effectFlow) {
        effectFlow?.collect { effect ->
            when(effect) {
                is LoginContract.Effect.Navigation.ToMainScreen ->
                    onEffectSend(LoginContract.Effect.Navigation.ToMainScreen)
                is LoginContract.Effect.Navigation.ToRegisterScreen ->{
                    onEffectSend(LoginContract.Effect.Navigation.ToRegisterScreen(effect.userNum))
                }
            }
        }
    }

    LaunchedEffect(state.loginState) {
        when(state.loginState){
            LoginState.LOADING -> { isLoading = true }
            LoginState.INIT -> { isLoading = false }
            LoginState.FIRST_USER -> { isLoading = false }
            LoginState.EXIST_USER -> { onEventSend(LoginContract.Event.NavigationToMain) }
            LoginState.REGISTER -> {
                val userNum = state.userNum

                if(!userNum.isNullOrBlank()) {
                    onEventSend(LoginContract.Event.NavigationToRegister(userNum))
                }
            }
            LoginState.ERROR -> {
                isLoading = false
                snackBarHostState.showSnackbar(
                    message = "[Error] 로그인을 할 수 없습니다.",
                    duration = SnackbarDuration.Short
                )
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackBarHostState) }
    ) { paddingValues->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues = paddingValues)
            ,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if(isLoading) LoadingDialog()
            Image(
                painter = painterResource(id = R.drawable.ic_app),
                contentDescription = "app icon"
            )

            Spacer(modifier = Modifier.size(50.dp))

            LoginBox(
                painterId = R.drawable.ic_login_kakao,
                platform = KAKAO,
                onLoginClick = { onEventSend(LoginContract.Event.KakaoLogin) }
            )
            Spacer(modifier = Modifier.size(12.dp))
            LoginBox(
                painterId = R.drawable.ic_login_naver,
                platform = NAVER,
                onLoginClick = { onEventSend(LoginContract.Event.NaverLogin(context)) }
            )
        }
    }
}



