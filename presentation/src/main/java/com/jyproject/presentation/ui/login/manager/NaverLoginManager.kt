package com.jyproject.presentation.ui.login.manager

import android.content.Context
import com.jyproject.presentation.BuildConfig
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback

class NaverLoginManager (
    private val context: Context
){
    companion object {
        const val NAVER_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val NAVER_CLIENT_KEY = BuildConfig.NAVER_CLIENT_KEY
    }

    fun startLogin(updateSocialToken: (String?) -> Unit) {
        naverSetOAuthLoginCallBack { updateSocialToken(it) }

        NaverIdLoginSDK.initialize(
            context = context,
            NAVER_CLIENT_ID,
            NAVER_CLIENT_KEY,
            "PlacePick"
        )
    }

    private lateinit var oAuthLoginCallBack: OAuthLoginCallback
    private fun naverSetOAuthLoginCallBack(updateSocialToken: (String?) -> Unit){
        oAuthLoginCallBack = object: OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) = onFailure(errorCode, message)

            override fun onFailure(httpStatus: Int, message: String) = updateSocialToken(null)

            override fun onSuccess()  = updateSocialToken(NaverIdLoginSDK.getAccessToken() ?: "")
        }
    }

}