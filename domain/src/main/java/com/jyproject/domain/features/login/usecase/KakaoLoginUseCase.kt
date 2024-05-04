package com.jyproject.domain.features.login.usecase

import com.jyproject.domain.features.login.repository.KakaoLoginRepository
import javax.inject.Inject

class KakaoLoginUseCase @Inject constructor(
    private val kakaoLoginRepository: KakaoLoginRepository
) {
    operator fun invoke(
        updateSocialToken: (String?) -> Unit,
        userNum: (String?) -> Unit
    ){
        kakaoLoginRepository.startKakaoLogin(updateSocialToken, userNum)
    }
}