package com.jyproject.placepick

import android.app.Application
import android.util.Log
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.kakao.sdk.common.KakaoSdk
import com.kakao.sdk.common.util.Utility
import com.naver.maps.map.NaverMapSdk
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ApplicationClass: Application() {
    override fun onCreate() {
        super.onCreate()

        KakaoSdk.init(this, BuildConfig.KAKAO_NATIVE_KEY)
        NaverMapSdk.getInstance(this).client =
            NaverMapSdk.NaverCloudPlatformClient(BuildConfig.NAVER_MAP_CLIENT_ID)
    }
}