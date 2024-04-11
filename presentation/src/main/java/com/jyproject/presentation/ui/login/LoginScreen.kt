package com.jyproject.presentation.ui.login

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jyproject.domain.models.LoginState
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.login.Platform.KAKAO
import com.jyproject.presentation.ui.login.Platform.NAVER

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun LoginScreen(
    context: Context,
    viewModel: LoginViewModel = hiltViewModel(),
    isLogin: () -> Unit
) {
    val loginState by viewModel.loginFlow.collectAsStateWithLifecycle()
    val snackBarHostState = remember { SnackbarHostState() }

    when(loginState){
        LoginState.LOADING -> {}
        LoginState.EXIST -> isLogin()
        LoginState.INIT -> {}
        LoginState.ERROR -> {
            LaunchedEffect(loginState){
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
            Image(
                painter = painterResource(id = R.drawable.ic_app),
                contentDescription = "app icon"
            )

            Spacer(modifier = Modifier.size(50.dp))

            LoginBox(
                painterId = R.drawable.ic_login_kakao,
                platform = KAKAO,
                onLoginClick = { viewModel.startKakaoLogin() }
            )
            Spacer(modifier = Modifier.size(12.dp))
            LoginBox(
                painterId = R.drawable.ic_login_naver,
                platform = NAVER,
                onLoginClick = { viewModel.startNaverLogin(context = context) }
            )
        }
    }
}

@Composable fun LoginBox(
    painterId: Int,
    platform: String,
    onLoginClick: (String) -> Unit
){
    val title = platform + "로 시작하기"
    val barColor =
        when(platform){
            KAKAO -> R.color.kakao_login
            NAVER -> R.color.naver_login
            else -> R.color.black
        }
    val textColor =
        when(platform){
            KAKAO -> Color.Black
            NAVER -> Color.White
            else -> Color.Transparent
        }

    Button(
        modifier = Modifier
            .width(310.dp)
            .height(50.dp)
        ,
        onClick = { onLoginClick(platform) },
        colors = ButtonDefaults.buttonColors(colorResource(id = barColor)),
        shape = RoundedCornerShape(8.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = painterId),
                contentDescription = platform
            )
            Text(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = 6.dp)
                ,
                text = title,
                color = textColor,
                fontWeight = FontWeight.Bold
            )

        }
    }
}