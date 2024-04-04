package com.jyproject.domain.features.login

interface NaverLoginRepository {
    fun startNaverLogin(context: Any, updateSocialToken: (String?) -> Unit)
}