package com.jyproject.data.features.login

import android.content.Context
import android.util.Log
import com.jyproject.data.BuildConfig
import com.jyproject.domain.features.login.NaverLoginRepository
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.OAuthLoginCallback
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class NaverLoginRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
): NaverLoginRepository {
    companion object {
        const val NAVER_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val NAVER_CLIENT_KEY = BuildConfig.NAVER_CLIENT_KEY
    }
    override fun startNaverLogin(updateSocialToken: (String?) -> Unit) {
        naverSetOAuthLoginCallBack { updateSocialToken(it) }

        NaverIdLoginSDK.initialize(
            context = context,
            NAVER_CLIENT_ID,
            NAVER_CLIENT_KEY,
            "PlacePick"
        )
        NaverIdLoginSDK.authenticate(context, oAuthLoginCallBack)
    }

    private lateinit var oAuthLoginCallBack: OAuthLoginCallback
    private fun naverSetOAuthLoginCallBack(updateSocialToken: (String?) -> Unit){
        oAuthLoginCallBack = object: OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) = onFailure(errorCode, message)

            override fun onFailure(httpStatus: Int, message: String) = updateSocialToken(null)

            override fun onSuccess() = updateSocialToken(NaverIdLoginSDK.getAccessToken())
        }
    }
}