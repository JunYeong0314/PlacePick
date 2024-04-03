package com.jyproject.domain.features.login

interface KakaoLoginRepository {
    fun startKakaoLogin(updateSocialToken: (String?) -> Unit)
}