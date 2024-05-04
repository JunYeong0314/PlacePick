package com.jyproject.data.features.login

import android.content.Context
import com.jyproject.data.BuildConfig
import com.jyproject.domain.features.login.repository.NaverLoginRepository
import com.navercorp.nid.NaverIdLoginSDK
import com.navercorp.nid.oauth.NidOAuthLogin
import com.navercorp.nid.oauth.OAuthLoginCallback
import com.navercorp.nid.profile.NidProfileCallback
import com.navercorp.nid.profile.data.NidProfileResponse
import javax.inject.Inject

class NaverLoginRepositoryImpl @Inject constructor(): NaverLoginRepository {
    companion object {
        const val NAVER_CLIENT_ID = BuildConfig.NAVER_CLIENT_ID
        const val NAVER_CLIENT_KEY = BuildConfig.NAVER_CLIENT_KEY
    }
    override fun startNaverLogin(
        context: Any,
        updateSocialToken: (String?) -> Unit,
        userNum: (String?) -> Unit
    ) {
        naverSetOAuthLoginCallBack(updateSocialToken, userNum)
        var curContext: Context? = null
        if(context is Context) curContext = context

        curContext?.let { cxt ->
            NaverIdLoginSDK.initialize(
                context = cxt,
                NAVER_CLIENT_ID,
                NAVER_CLIENT_KEY,
                "PlacePick"
            )
            NaverIdLoginSDK.authenticate(cxt, oAuthLoginCallBack)
        }
    }

    private lateinit var oAuthLoginCallBack: OAuthLoginCallback
    private fun naverSetOAuthLoginCallBack(
        updateSocialToken: (String?) -> Unit,
        userNum: (String?) -> Unit
    ){
        oAuthLoginCallBack = object: OAuthLoginCallback {
            override fun onError(errorCode: Int, message: String) = onFailure(errorCode, message)

            override fun onFailure(httpStatus: Int, message: String) = updateSocialToken(null)

            override fun onSuccess(){
                updateSocialToken(NaverIdLoginSDK.getAccessToken())
                getUserNum(userNum)
            }
        }
    }

    private fun getUserNum(userNum: (String?) -> Unit){
        NidOAuthLogin().callProfileApi(object : NidProfileCallback<NidProfileResponse>{
            override fun onError(errorCode: Int, message: String) {}
            override fun onFailure(httpStatus: Int, message: String) {}
            override fun onSuccess(result: NidProfileResponse) {
                userNum(result.profile?.id.toString())
            }
        })
    }
}