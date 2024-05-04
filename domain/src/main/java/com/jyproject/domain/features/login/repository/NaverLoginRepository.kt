package com.jyproject.domain.features.login.repository

interface NaverLoginRepository {
    fun startNaverLogin(context: Any, updateSocialToken: (String?) -> Unit, userNum: (String?) -> Unit)
}