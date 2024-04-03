package com.jyproject.presentation.ui.login.manager

import android.content.Context
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient

class KakaoLoginManager(
    private val context: Context
) {

    private lateinit var kakaoLoginCallBack: (OAuthToken?, Throwable?) -> Unit

    fun startKakaoLogin(updateSocialToken: (String?) -> Unit){
        kakaoLoginCallBack = getLoginCallBack(updateSocialToken)

        when(getKakaoLoginState()){
            KakaoLoginState.KAKAO_APP_LOGIN -> onKakaoAppLogin(updateSocialToken)
            KakaoLoginState.KAKAO_WEB_LOGIN -> onKakaoWebLogin()
        }
    }

    // 앱 설치 여부 확인
    private fun getKakaoLoginState(): KakaoLoginState =
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)) KakaoLoginState.KAKAO_APP_LOGIN
        else KakaoLoginState.KAKAO_WEB_LOGIN

    private fun getLoginCallBack(updateSocialToken: (String?) -> Unit): (OAuthToken?, Throwable?) -> Unit {
        return {token, error ->
            if(error != null) updateSocialToken(null)
            if(token != null) updateSocialToken(token.accessToken)
        }
    }

    private fun onKakaoAppLogin(updateSocialToken: (String?) -> Unit){
        UserApiClient.instance.loginWithKakaoTalk(context){ token, error ->
            if(error != null) updateSocialToken(null)
            if(token != null) updateSocialToken(token.accessToken)
        }
    }

    private fun onKakaoWebLogin(){
        UserApiClient.instance.loginWithKakaoAccount(context, callback = kakaoLoginCallBack)
    }
}

enum class KakaoLoginState{
    KAKAO_APP_LOGIN, KAKAO_WEB_LOGIN
}