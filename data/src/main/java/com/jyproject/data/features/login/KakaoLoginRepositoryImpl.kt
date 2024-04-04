package com.jyproject.data.features.login

import android.content.Context
import android.util.Log
import com.jyproject.domain.features.login.KakaoLoginRepository
import com.jyproject.domain.models.KakaoLoginState
import com.kakao.sdk.auth.model.OAuthToken
import com.kakao.sdk.user.UserApiClient
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class KakaoLoginRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): KakaoLoginRepository {
    private lateinit var kakaoLoginCallBack: (OAuthToken?, Throwable?) -> Unit
    override fun startKakaoLogin(updateSocialToken: (String?) -> Unit) {
        kakaoLoginCallBack = getLoginCallBack(updateSocialToken)

        when(getKakaoLoginState()){
            KakaoLoginState.APP -> onKakaoAppLogin(updateSocialToken)
            KakaoLoginState.WEB -> onKakaoWebLogin()
        }
    }

    private fun getKakaoLoginState(): KakaoLoginState =
        if(UserApiClient.instance.isKakaoTalkLoginAvailable(context)) KakaoLoginState.APP
        else KakaoLoginState.WEB

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