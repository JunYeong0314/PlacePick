package com.jyproject.domain.features.login

interface NaverLoginRepository {
    fun startNaverLogin(updateSocialToken: (String?) -> Unit)
}