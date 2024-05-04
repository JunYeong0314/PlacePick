package com.jyproject.domain.features.login.repository

interface KakaoLoginRepository {
    fun startKakaoLogin(updateSocialToken: (String?) -> Unit, userNum: (String?) -> Unit)
}