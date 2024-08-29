package com.jyproject.presentation.ui.feature.register

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jyproject.presentation.R
import com.jyproject.presentation.ui.feature.common.util.CircularProgress
import com.jyproject.presentation.ui.feature.register.composable.NickTextField
import com.jyproject.presentation.ui.feature.register.composable.RegisterTopBar
import kotlinx.coroutines.flow.Flow

@Composable
fun RegisterScreen(
    userNum: String,
    state: RegisterContract.State,
    effectFlow: Flow<RegisterContract.Effect>?,
    onEventSend: (event: RegisterContract.Event) -> Unit,
    onEffectSend: (effect: RegisterContract.Effect) -> Unit
){
    var nickText by remember { mutableStateOf("") }
    var nickLength by remember { mutableIntStateOf(0) }
    var completeButtonColor by remember {
        mutableIntStateOf(R.color.light_gray_middle1)
    }
    var isComplete by remember { mutableStateOf(false) }
    var isLoading by remember { mutableStateOf(false) }
    var checkNickMsg by remember { mutableStateOf("닉네임을 입력하세요") }
    var checkNickMsgColor by remember { mutableStateOf(Color.Red) }

    LaunchedEffect(nickText) {
        nickLength = nickText.length
    }

    LaunchedEffect(state.registerState) {
        when(state.registerState) {
            RegisterState.INIT -> {
                completeButtonColor = R.color.light_gray_middle1
                isComplete = false
            }
            RegisterState.LOADING -> isLoading = true
            RegisterState.SUCCESS -> {}
            RegisterState.NICK_SUCCESS -> {
                completeButtonColor = R.color.app_base
                isComplete = true
            }
            RegisterState.NETWORK_ERROR -> {}
            RegisterState.ERROR -> {}
        }
    }
    
    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        topBar = { RegisterTopBar() }
    ) { innerPadding->
        if (isLoading) CircularProgress()
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 12.dp, vertical = 8.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NickTextField(
                nickText = nickText,
                nickLength = nickLength,
                checkNickMsg = checkNickMsg,
                onValueChange = {
                    if (it.length <= 10) {
                        nickText = it
                        onEventSend(RegisterContract.Event.OnNickCheck(nickText))
                    }
                }
            )
            Spacer(modifier = Modifier.size(24.dp))
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp, vertical = 8.dp),
                onClick = { onEventSend(RegisterContract.Event.NavigateToMain) },
                shape = RoundedCornerShape(8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = colorResource(id = completeButtonColor)
                ),
                enabled = isComplete
            ) {
                Text(
                    text = "완료",
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            }
        }
    }

}